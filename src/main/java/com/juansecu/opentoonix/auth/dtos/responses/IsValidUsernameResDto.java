package com.juansecu.opentoonix.auth.dtos.responses;

import lombok.Data;

@Data
public class IsValidUsernameResDto {
    private boolean existsUsername;
    private boolean usernameInBlackList;
}
