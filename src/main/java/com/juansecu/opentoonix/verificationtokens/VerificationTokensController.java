package com.juansecu.opentoonix.verificationtokens;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;

@RestController
@RequestMapping("/verification-tokens")
@RequiredArgsConstructor
public class VerificationTokensController {
    private final VerificationTokensApiService verificationTokensApiService;

    @GetMapping("/verify")
    public void verifyToken(
        @RequestParam("token") final String token,
        @RequestParam("type") final EVerificationTokenType verificationTokenType,
        @RequestParam("userId") final Integer userId,
        final HttpServletRequest request
    ) {
        this.verificationTokensApiService.verifyToken(
            verificationTokenType,
            request
        );
    }
}
