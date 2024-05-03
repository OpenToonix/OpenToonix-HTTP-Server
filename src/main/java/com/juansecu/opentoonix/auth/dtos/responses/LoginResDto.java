package com.juansecu.opentoonix.auth.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import com.juansecu.opentoonix.auth.enums.ELoggingInError;
import com.juansecu.opentoonix.shared.dtos.responses.BaseResDto;
import com.juansecu.opentoonix.users.models.UserInformationModel;

@Getter
public class LoginResDto extends BaseResDto<ELoggingInError> {
    @JsonProperty("principal")
    private final UserInformationModel userInformation;

    public LoginResDto(
        final ELoggingInError error,
        final boolean success,
        final UserInformationModel userInformation
    ) {
        super(error, success);
        this.userInformation = userInformation;
    }
}
