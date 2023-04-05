package com.juansecu.opentoonix.user.dtos.requests;

import lombok.Data;

@Data
public class LoginReqDto {
    private String password;
    private String username;
}
