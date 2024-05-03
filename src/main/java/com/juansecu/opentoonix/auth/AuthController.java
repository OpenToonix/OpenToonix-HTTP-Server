package com.juansecu.opentoonix.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.juansecu.opentoonix.auth.dtos.requests.LoginReqDto;
import com.juansecu.opentoonix.auth.dtos.requests.RegisterReqDto;
import com.juansecu.opentoonix.auth.dtos.responses.IsValidUsernameResDto;
import com.juansecu.opentoonix.auth.dtos.responses.LoginResDto;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @GetMapping("/is-valid-username")
    public IsValidUsernameResDto isValidUsername(@RequestParam("username") final String username) {
        return this.authService.isValidUsername(username);
    }

    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        path = "/login"
    )
    public LoginResDto login(
        @Valid final LoginReqDto loginReqDto,
        final HttpServletRequest request
    ) {
        return this.authService.login(loginReqDto, request);
    }

    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        path = "/register"
    )
    public void register(@Valid final RegisterReqDto registerReqDto) {
        this.authService.createUser(registerReqDto);
    }
}
