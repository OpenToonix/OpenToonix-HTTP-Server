package com.juansecu.opentoonix.verificationtokens;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import com.juansecu.opentoonix.shared.utils.CryptoUtil;
import com.juansecu.opentoonix.shared.utils.TimeUtil;
import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;
import com.juansecu.opentoonix.verificationtokens.models.entities.VerificationTokenEntity;
import com.juansecu.opentoonix.verificationtokens.repositories.IVerificationTokensRepository;

@RequiredArgsConstructor
@Service
public class VerificationTokensService {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(VerificationTokensService.class);

    @Autowired
    @Qualifier("verificationTokensTextEncryptor")
    private TextEncryptor verificationTokensTextEncryptor;

    private final CryptoUtil cryptoUtil;
    private final IVerificationTokensRepository verificationTokensRepository;

    public VerificationTokenEntity generateVerificationToken(
        final UserEntity user,
        final EVerificationTokenType verificationTokenType,
        final Optional<String> metadata
    ) {
        LocalDateTime tokenExpirationDateTime;
        long tokenExpirationMilliseconds;
        VerificationTokenEntity verificationToken;

        final UUID token = UUID.randomUUID();

        VerificationTokensService.CONSOLE_LOGGER.info(
            "Generating {}...",
            verificationTokenType
        );

        tokenExpirationDateTime = switch (verificationTokenType) {
            case DELETE_ACCOUNT_TOKEN -> LocalDateTime
                .now()
                .plusMinutes(
                    VerificationTokensConstants.MINUTES_OF_EXPIRING_DELETE_ACCOUNT_TOKEN
                );
            case EMAIL_VERIFICATION_TOKEN -> LocalDateTime
                .now()
                .plusHours(
                    VerificationTokensConstants.HOURS_OF_EXPIRING_EMAIL_VERIFICATION_TOKEN
                );
            case RESET_PASSWORD_TOKEN -> LocalDateTime
                .now()
                .plusMinutes(
                    VerificationTokensConstants.MINUTES_OF_EXPIRING_RESET_PASSWORD_TOKEN
                );
        };

        tokenExpirationMilliseconds = TimeUtil.localDateTimeToMilliseconds(
            tokenExpirationDateTime
        );

        VerificationTokensService.CONSOLE_LOGGER.info(
            "Saving {}...",
            verificationTokenType
        );

        verificationToken = metadata
            .map(metadataValue -> new VerificationTokenEntity(
                token,
                verificationTokenType,
                metadataValue,
                new Date(tokenExpirationMilliseconds),
                user
            ))
            .orElseGet(() -> new VerificationTokenEntity(
                token,
                verificationTokenType,
                new Date(tokenExpirationMilliseconds),
                user
            ));

        return this.verificationTokensRepository.save(verificationToken);
    }

    public String getEncryptedToken(final String decryptedToken) {
        return this.cryptoUtil.encrypt(
            decryptedToken,
            this.verificationTokensTextEncryptor
        );
    }

    public VerificationTokenEntity getVerificationToken(
        final String encryptedToken,
        final EVerificationTokenType verificationTokenType,
        final UserEntity user
    ) {
        VerificationTokensService.CONSOLE_LOGGER.info(
            "Getting {} for user {}...",
            verificationTokenType,
            user.getUsername()
        );

        UUID decryptedToken;

        try {
            decryptedToken = UUID.fromString(
                this.cryptoUtil.decrypt(
                    encryptedToken,
                    this.verificationTokensTextEncryptor
                )
            );
        } catch (final IllegalArgumentException illegalArgumentException) {
            VerificationTokensService.CONSOLE_LOGGER.info(
                "Invalid token"
            );

            return null;
        }

        return this.verificationTokensRepository
            .findByTokenAndTypeAndUser(
                decryptedToken,
                verificationTokenType,
                user
            )
            .orElse(null);
    }

    public void verifyToken(
        final EVerificationTokenType verificationTokenType,
        final String username,
        final HttpServletRequest request
    ) {
        if (
            (boolean) request.getAttribute(
                VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE_KEY
            )
        )
            return;

        final VerificationTokenEntity verificationToken = (VerificationTokenEntity) request.getAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_REQUEST_ATTRIBUTE_KEY
        );

        VerificationTokensService.CONSOLE_LOGGER.info(
            "Verifying {} for user {}...",
            verificationTokenType,
            username
        );

        if (
            verificationToken.getExpiresAt().getTime() < TimeUtil.localDateTimeToMilliseconds(
                LocalDateTime.now()
            )
        ) {
            VerificationTokensService.CONSOLE_LOGGER.info(
                "Verification token expired"
            );

            request.setAttribute(
                VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE_KEY,
                true
            );

            return;
        }

        VerificationTokensService.CONSOLE_LOGGER.info(
            "Verification token validated successfully. Updating uses count..."
        );

        verificationToken.setUsesCount(verificationToken.getUsesCount() + 1);

        this.verificationTokensRepository.save(verificationToken);

        request.setAttribute(
            VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE_KEY,
            false
        );
    }
}
