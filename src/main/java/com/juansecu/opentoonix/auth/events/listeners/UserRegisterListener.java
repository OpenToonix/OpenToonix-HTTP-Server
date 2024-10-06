package com.juansecu.opentoonix.auth.events.listeners;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.juansecu.opentoonix.auth.events.UserRegisterEvent;
import com.juansecu.opentoonix.shared.providers.HostDetailsProvider;
import com.juansecu.opentoonix.shared.services.EmailService;
import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.verificationtokens.VerificationTokensConstants;
import com.juansecu.opentoonix.verificationtokens.VerificationTokensService;
import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;
import com.juansecu.opentoonix.verificationtokens.models.EmailVerificationTokenMetadataModel;
import com.juansecu.opentoonix.verificationtokens.models.entities.VerificationTokenEntity;

@Component
@RequiredArgsConstructor
public class UserRegisterListener implements ApplicationListener<UserRegisterEvent> {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(UserRegisterListener.class);

    @Value("${mail.new-account-email-verification-message.message}")
    private String newAccountEmailVerificationMessage;
    @Value("${mail.new-account-email-verification-message.subject}")
    private String newAccountEmailVerificationSubject;

    private final EmailService emailService;
    private final HostDetailsProvider hostDetailsProvider;
    private final ObjectMapper objectMapper;
    private final VerificationTokensService verificationTokensService;

    @Override
    public void onApplicationEvent(
        final UserRegisterEvent userRegisterEvent
    ) {
        String verificationEmailMessage;
        VerificationTokenEntity verificationToken;
        String verificationTokenMetadata;

        final UserEntity newUser = userRegisterEvent.getUser();

        try {
            verificationTokenMetadata = this.objectMapper.writeValueAsString(
                new EmailVerificationTokenMetadataModel(
                    true
                )
            );
        } catch (final JsonProcessingException jsonProcessingException) {
            UserRegisterListener.CONSOLE_LOGGER.error(
                "Could not serialize verification token metadata: {}",
                jsonProcessingException.getMessage()
            );

            return;
        }

        verificationToken = this.verificationTokensService.generateVerificationToken(
            newUser,
            EVerificationTokenType.EMAIL_VERIFICATION_TOKEN,
            Optional.of(verificationTokenMetadata)
        );
        verificationEmailMessage = this.replaceEmailVerificationParameters(
            this.verificationTokensService.getEncryptedToken(
                verificationToken.getToken().toString()
            ),
            newUser.getUsername()
        );

        UserRegisterListener.CONSOLE_LOGGER.info(
            "Sending verification email..."
        );

        if (
            !this.emailService.sendSimpleAccountManagementMessage(
                verificationEmailMessage,
                this.newAccountEmailVerificationSubject,
                newUser.getEmail()
            )
        ) {
            UserRegisterListener.CONSOLE_LOGGER.error(
                "Verification email could not be sent"
            );

            return;
        }

        UserRegisterListener.CONSOLE_LOGGER.info(
            "Verification email sent"
        );
    }

    private String replaceEmailVerificationParameters(
        final String encryptedToken,
        final String username
    ) {
        return this.newAccountEmailVerificationMessage
            .replace(
                "{hours_to_expire}",
                String.valueOf(
                    VerificationTokensConstants.HOURS_OF_EXPIRING_EMAIL_VERIFICATION_TOKEN
                )
            )
            .replace("{username}", username)
            .replace(
                "{user_account_verification_link}",
                this.hostDetailsProvider.getPublicAddress() +
                    "/api/verification-tokens/verify?token=" +
                    encryptedToken +
                    "&type=" +
                    EVerificationTokenType.EMAIL_VERIFICATION_TOKEN +
                    "&username=" +
                    username
            );
    }
}
