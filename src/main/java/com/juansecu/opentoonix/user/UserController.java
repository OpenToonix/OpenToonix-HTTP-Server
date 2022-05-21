package com.juansecu.opentoonix.user;

/* --- Javax modules --- */
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.dtos.requests.LoginReqDto;
import com.juansecu.opentoonix.user.dtos.requests.NewUserReqDto;
import com.juansecu.opentoonix.user.dtos.responses.IsValidUsernameResDto;
import com.juansecu.opentoonix.user.dtos.responses.LoginResDto;

/**
 * Contains all endpoints related to users.
 */
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

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
    public LoginResDto postLogin(
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
}
