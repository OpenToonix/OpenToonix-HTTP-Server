package com.juansecu.opentoonix.user.dtos.requests;

/* --- Javax modules --- */
import javax.validation.constraints.*;

/* --- Third-party modules --- */
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.enums.EUserGender;

/**
 * Represents new user's data.
 */
@Data
public class NewUserReqDto {
    @Min(
        message = "Your age must be greater than or equal to 13",
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
        max = 75,
        message = "Email length must be less than or equal to 75"
    )
    private String email;
    private EUserGender gender;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(
        flags = {Pattern.Flag.CASE_INSENSITIVE},
        message = "Password must contain alphanumeric characters",
        regexp = "^(?=.*[a-z])[0-9a-z]+$"
    )
    @Size(
        max = 30,
        message = "Password must be a length between 6 and 30 characters",
        min = 6
    )
    private String password;
    @NotBlank(message = "Username cannot be empty")
    @Size(
        max = 12,
        message = "Username must be a length between 4 and 12 characters",
        min = 4
    )
    private String username;
}
