package com.juansecu.opentoonix.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
    description = "Authentication operations",
    name = "Auth"
)
public class AuthController {
    private final AuthService authService;

    @ApiResponse(
        description = "Returns whether the username is valid or not.",
        responseCode = "200"
    )
    @ApiResponse(
        description = "There was an unexpected error while trying to validate the username.",
        responseCode = "500"
    )
    @Operation(
        description = "Validates the username.",
        parameters = {
            @Parameter(
                description = "The username to validate.",
                name = "username",
                required = true,
                schema = @Schema(
                    type = "string"
                )
            )
        },
        summary = "Validates the username"
    )
    @GetMapping("/is-valid-username")
    public IsValidUsernameResDto isValidUsername(@RequestParam("username") final String username) {
        return this.authService.isValidUsername(username);
    }

    @ApiResponse(
        description = "Authenticates the user and returns the authentication token.",
        responseCode = "200"
    )
    @ApiResponse(
        description = "The username or password is incorrect.",
        responseCode = "403"
    )
    @ApiResponse(
        description = "There was an unexpected error while trying to authenticate the user.",
        responseCode = "500"
    )
    @Operation(
        description = "Authenticates the user.",
        summary = "Authenticates the user"
    )
    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        path = "/login"
    )
    @RequestBody(
        content = {
            @Content(
                mediaType = "application/x-www-form-urlencoded",
                schema = @Schema(
                    implementation = LoginReqDto.class
                )
            )
        }
    )
    public LoginResDto login(
        @Valid final LoginReqDto loginReqDto,
        final HttpServletRequest request
    ) {
        return this.authService.login(loginReqDto, request);
    }

    @ApiResponse(
        description = "Registers a new user.",
        responseCode = "200"
    )
    @ApiResponse(
        description = "The username or email address is already in use.",
        responseCode = "409"
    )
    @ApiResponse(
        description = "There was an unexpected error while trying to register the new user.",
        responseCode = "500"
    )
    @Operation(
        description = "Registers a new user.",
        summary = "Registers a new user"
    )
    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        path = "/register"
    )
    @RequestBody(
        content = {
            @Content(
                mediaType = "application/x-www-form-urlencoded",
                schema = @Schema(
                    implementation = RegisterReqDto.class
                )
            )
        }
    )
    public void register(@Valid final RegisterReqDto registerReqDto) {
        this.authService.createUser(registerReqDto);
    }
}
