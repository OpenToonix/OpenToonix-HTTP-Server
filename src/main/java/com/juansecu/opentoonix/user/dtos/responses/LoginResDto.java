package com.juansecu.opentoonix.user.dtos.responses;

/* --- Third-party modules --- */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/* --- Application modules --- */
import com.juansecu.opentoonix.shared.dtos.responses.BaseResDto;
import com.juansecu.opentoonix.user.enums.ELoggingInError;
import com.juansecu.opentoonix.user.models.UserInformationModel;

/**
 * Represents the information sent to the client
 * when the user signs in.
 */
@Getter
public class LoginResDto extends BaseResDto<ELoggingInError> {
    @JsonProperty("principal")
    private final UserInformationModel userInformation;

    public LoginResDto(ELoggingInError error, boolean success, UserInformationModel userInformation) {
        super(error, success);
        this.userInformation = userInformation;
    }
}
