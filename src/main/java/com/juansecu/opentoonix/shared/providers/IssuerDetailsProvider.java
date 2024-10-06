package com.juansecu.opentoonix.shared.providers;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class IssuerDetailsProvider {
    @Value("${issuer.account-related.name}")
    private String issuerAccountRelatedName;
    @Value("${issuer.name}")
    private String issuerName;
    @Value("${issuer.support-email-address}")
    private String issuerSupportEmailAddress;
}
