package com.juansecu.opentoonix.auth.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginReqDto {
    @NotEmpty(message = "Password cannot be empty")
    @Size(
        max = 32,
        message = "Password must be a length between 8 and 32 characters",
        min = 8
    )
    private String password;
    @NotEmpty(message = "Username cannot be empty")
    @Pattern(
        flags = {Pattern.Flag.CASE_INSENSITIVE},
        message = "Invalid username",
        regexp = "^[0-9a-z-_]*$"
    )
    @Size(
        max = 12,
        message = "Username must be a length between 4 and 12 characters",
        min = 4
    )
    private String username;
}
