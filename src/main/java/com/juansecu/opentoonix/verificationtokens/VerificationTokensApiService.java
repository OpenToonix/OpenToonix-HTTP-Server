package com.juansecu.opentoonix.verificationtokens;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.juansecu.opentoonix.users.UsersConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class VerificationTokensApiService {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(VerificationTokensApiService.class);

    private final CryptoUtil cryptoUtil;
    private final IVerificationTokensRepository verificationTokensRepository;
    private final TextEncryptor verificationTokensTextEncryptor;

    public VerificationTokenEntity generateVerificationToken(
        final UserEntity user,
        final EVerificationTokenType verificationTokenType,
        final Optional<String> metadata
    ) {
        LocalDateTime tokenExpirationDateTime;
        long tokenExpirationMilliseconds;
        VerificationTokenEntity verificationToken;

        final UUID token = UUID.randomUUID();

        VerificationTokensApiService.CONSOLE_LOGGER.info(
            "Generating {} for user {}...",
            verificationTokenType,
            user.getUsername()
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

        VerificationTokensApiService.CONSOLE_LOGGER.info(
            "Saving {} for user {}...",
            verificationTokenType,
            user.getUsername()
        );

        verificationToken = metadata
            .map(metadataValue -> new VerificationTokenEntity(
                token,
                verificationTokenType,
                metadataValue,
                new Date(tokenExpirationMilliseconds),
                user.getUserId()
            ))
            .orElseGet(() -> new VerificationTokenEntity(
                token,
                verificationTokenType,
                user.getUserId(),
                new Date(tokenExpirationMilliseconds)
            ));

        VerificationTokensApiService.CONSOLE_LOGGER.info(
            "Verification token of type {} for user {} saved successfully",
            verificationTokenType,
            user.getUsername()
        );

        return this.verificationTokensRepository.save(verificationToken);
    }

    public String getEncryptedToken(final String decryptedToken) {
        return this.cryptoUtil.encrypt(
            decryptedToken,
            this.verificationTokensTextEncryptor
        );
    }

    public Optional<VerificationTokenEntity> getVerificationToken(
        final String encryptedToken,
        final EVerificationTokenType verificationTokenType,
        final UserEntity user
    ) {
        VerificationTokensApiService.CONSOLE_LOGGER.info(
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
            VerificationTokensApiService.CONSOLE_LOGGER.info(
                "Invalid token"
            );

            return Optional.empty();
        }

        return this.verificationTokensRepository
            .findByTokenAndTypeAndUser(
                decryptedToken,
                verificationTokenType,
                user
            );
    }

    public void verifyToken(
        final EVerificationTokenType verificationTokenType,
        final HttpServletRequest request
    ) {
        if (
            (boolean) request.getAttribute(
                VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE
            )
        )
            return;

        final UserEntity user = (UserEntity) request.getAttribute(
            UsersConstants.USER_REQUEST_ATTRIBUTE
        );
        final VerificationTokenEntity verificationToken = (VerificationTokenEntity) request.getAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_REQUEST_ATTRIBUTE
        );

        VerificationTokensApiService.CONSOLE_LOGGER.info(
            "Verifying {} for user {}...",
            verificationTokenType,
            user.getUsername()
        );

        if (
            verificationToken.getExpiresAt().getTime() < TimeUtil.localDateTimeToMilliseconds(
                LocalDateTime.now()
            )
        ) {
            VerificationTokensApiService.CONSOLE_LOGGER.info(
                "Verification token expired"
            );

            request.setAttribute(
                VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE,
                true
            );

            return;
        } else if (
            verificationTokenType == EVerificationTokenType.EMAIL_VERIFICATION_TOKEN &&
            verificationToken.getUsesCount() >= 1
        ) {
            VerificationTokensApiService.CONSOLE_LOGGER.info(
                "Verification token already used"
            );

            request.setAttribute(
                VerificationTokensConstants.IS_ALREADY_USED_TOKEN_VIEW_ATTRIBUTE,
                true
            );
            request.setAttribute(
                VerificationTokensConstants.MESSAGE_VIEW_ATTRIBUTE_KEY,
                "Your email address has already been verified"
            );

            return;
        }

        VerificationTokensApiService.CONSOLE_LOGGER.info(
            "Verification token validated successfully. Updating uses count..."
        );

        verificationToken.setUsesCount(verificationToken.getUsesCount() + 1);

        this.verificationTokensRepository.save(verificationToken);
    }
}
