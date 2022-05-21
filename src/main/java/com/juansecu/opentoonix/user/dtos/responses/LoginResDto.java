package com.juansecu.opentoonix.user.dtos.responses;

/* --- Third-party modules --- */
import lombok.AllArgsConstructor;
import lombok.Data;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.enums.ELoggingInError;
import com.juansecu.opentoonix.user.models.UserInformationModel;

/**
 * Represents the information sent to the client
 * when the user signs in.
 */
@AllArgsConstructor
@Data
public class LoginResDto {
    private boolean success;
    private ELoggingInError error;
    private UserInformationModel principal;
}
