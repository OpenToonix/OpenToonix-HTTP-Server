package com.juansecu.opentoonix.user.dtos.responses;

import lombok.Data;

@Data
public class IsValidUsernameResDto {
    private boolean existsUsername;
    private boolean usernameInBlackList;
}
