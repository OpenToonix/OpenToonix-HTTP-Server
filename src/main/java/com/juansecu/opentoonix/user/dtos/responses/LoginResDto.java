package com.juansecu.opentoonix.user.dtos.responses;

/* --- Third-party modules --- */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/* --- Application modules --- */
import com.juansecu.opentoonix.user.enums.ELoggingInError;
import com.juansecu.opentoonix.user.models.UserInformationModel;

/**
 * Represents the information sent to the client
 * when the user signs in.
 */
@AllArgsConstructor
@Getter
public class LoginResDto {
    private final ELoggingInError error;
    private final boolean success;
    @JsonProperty("principal")
    private final UserInformationModel userInformation;
}
