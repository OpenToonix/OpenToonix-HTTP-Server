package com.juansecu.opentoonix.user;

/* --- Javax modules --- */
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.util.Calendar;
import java.util.Locale;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.dtos.requests.LoginReqDto;
import com.juansecu.opentoonix.user.dtos.requests.NewUserReqDto;
import com.juansecu.opentoonix.user.dtos.responses.IsValidUsernameResDto;
import com.juansecu.opentoonix.user.dtos.responses.LoginResDto;
import com.juansecu.opentoonix.mail.VerificationToken;
import com.juansecu.opentoonix.user.models.entities.UserEntity;



/**
 * Contains all endpoints related to users.
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messages;

    /**
     * Endpoint to verify if the username is valid.
     *
     * @param username Username to verify
     * @return IsValidUsernameResDto
     */
    @GetMapping("/is-valid-username")
    public IsValidUsernameResDto isValidUsername(@RequestParam("username") final String username) {
        return this.userService.isValidUsername(username);
    }

    /**
     * Endpoint to sign in an existent user.
     *
     * @param loginReqDto The login information
     * @param request The request object
     * @return LoginResDto
     */
    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        path = "/login"
    )
    public LoginResDto login(
        @Valid final LoginReqDto loginReqDto,
        final HttpServletRequest request
    ) {
        return this.userService.login(loginReqDto, request);
    }

    /**
     * Endpoint to create a new user and save it in the database.
     *
     * @param newUserReqDto New user's data
     */
    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        path = "/save-user"
    )
    public void register(@Valid final NewUserReqDto newUserReqDto) {
        this.userService.createUser(newUserReqDto);
    }



    @GetMapping("/registrationConfirm")
    public String confirmRegistration
        (WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        UserEntity user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            //TODO redirect to new account creation page
            return "redirect:/blank";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/login";
    }
}
