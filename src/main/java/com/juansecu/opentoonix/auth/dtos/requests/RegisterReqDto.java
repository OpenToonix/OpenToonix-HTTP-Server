package com.juansecu.opentoonix.auth.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.juansecu.opentoonix.users.enums.EUserGender;

@Data
public class RegisterReqDto {
    @Min(
        message = "Age must be greater than or equal to 13",
        value = 13
    )
    @NotBlank(message = "Age cannot be empty")
    @Pattern(
        message = "Invalid age",
        regexp = "^\\d\\d$"
    )
    private String age;
    @NotBlank(message = "Avatar information cannot be empty")
    private String avatar;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthdate;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty")
    @Size(
        max = 50,
        message = "Email length must be less than or equal to 50 characters"
    )
    private String email;
    @NotBlank(message = "Gender cannot be empty")
    private EUserGender gender;
    @NotBlank(message = "Password cannot be empty")
    @Size(
        max = 32,
        message = "Password must be a length between 8 and 32 characters",
        min = 8
    )
    private String password;
    @NotBlank(message = "Username cannot be empty")
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
