package com.juansecu.opentoonix.user;

/* --- Javax modules --- */
import javax.validation.Valid;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.dtos.requests.NewUserReqDto;
import com.juansecu.opentoonix.user.dtos.responses.IsValidUsernameResDto;

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
    public IsValidUsernameResDto isValidUsername(@RequestParam("username") String username) {
        return this.userService.isValidUsername(username);
    }

    /**
     * Endpoint to create a new user and save it in the database.
     *
     * @param newUserDto New user's data
     */
    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        path = "/save-user"
    )
    public void register(@Valid NewUserReqDto newUserDto) {
        this.userService.createUser(newUserDto);
    }
}
