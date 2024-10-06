package com.juansecu.opentoonix.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class BeansProvider {
    @Value("${verification-tokens.salt-key}")
    private String verificationTokensSaltKey;
    @Value("${verification-tokens.security-key}")
    private String verificationTokensSecurityKey;

    @Bean
    protected ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "verificationTokensTextEncryptor")
    protected TextEncryptor verificationTokensTextEncryptor() {
        return Encryptors.text(
            this.verificationTokensSecurityKey,
            this.verificationTokensSaltKey
        );
    }
}
