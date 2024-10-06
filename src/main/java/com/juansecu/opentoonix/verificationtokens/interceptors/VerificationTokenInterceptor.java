package com.juansecu.opentoonix.verificationtokens.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.juansecu.opentoonix.auth.AuthService;
import com.juansecu.opentoonix.auth.enums.EBasicRole;
import com.juansecu.opentoonix.users.UsersConstants;
import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.verificationtokens.VerificationTokensConstants;
import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;
import com.juansecu.opentoonix.verificationtokens.models.EmailVerificationTokenMetadataModel;
import com.juansecu.opentoonix.verificationtokens.models.entities.VerificationTokenEntity;

@Component
@RequiredArgsConstructor
public class VerificationTokenInterceptor implements HandlerInterceptor {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(VerificationTokenInterceptor.class);

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    public void postHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler,
        final ModelAndView modelAndView
    ) {
        if (
            request.getAttribute(
                VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE_KEY
            ) == null
        ) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName(
                VerificationTokensConstants.VERIFICATION_TOKEN_VIEW_NAME
            );
            modelAndView.addObject(
                VerificationTokensConstants.IS_VALID_TOKEN_VIEW_ATTRIBUTE_KEY,
                false
            );
            modelAndView.addObject(
                VerificationTokensConstants.MESSAGE_VIEW_ATTRIBUTE_KEY,
                "Invalid token"
            );

            return;
        }

        VerificationTokenInterceptor.CONSOLE_LOGGER.info(
            "Post-intercepting token verification request..."
        );

        Object verificationTokenMetadata;

        final UserEntity user = (UserEntity) request.getAttribute(
            UsersConstants.USER_REQUEST_ATTRIBUTE_KEY
        );
        final boolean isInvalidToken = (boolean) request.getAttribute(
            VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE_KEY
        );
        final VerificationTokenEntity verificationToken = (VerificationTokenEntity) request.getAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_REQUEST_ATTRIBUTE_KEY
        );
        final EVerificationTokenType verificationTokenType = EVerificationTokenType.valueOf(
            request.getParameter(
                VerificationTokensConstants.TOKEN_TYPE_REQUEST_PARAMETER_KEY
            )
        );

        if (isInvalidToken) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            modelAndView.setViewName(
                VerificationTokensConstants.VERIFICATION_TOKEN_VIEW_NAME
            );
            modelAndView.addObject(
                VerificationTokensConstants.IS_VALID_TOKEN_VIEW_ATTRIBUTE_KEY,
                false
            );
            modelAndView.addObject(
                VerificationTokensConstants.MESSAGE_VIEW_ATTRIBUTE_KEY,
                "Invalid token"
            );

            return;
        }

        switch (verificationTokenType) {
            case DELETE_ACCOUNT_TOKEN -> {
                // TODO: Implement account deletion token handling
                modelAndView.setStatus(HttpStatus.NOT_IMPLEMENTED);
                modelAndView.setViewName(
                    VerificationTokensConstants.VERIFICATION_TOKEN_VIEW_NAME
                );
                modelAndView.addObject(
                    VerificationTokensConstants.IS_VALID_TOKEN_VIEW_ATTRIBUTE_KEY,
                    false
                );
                modelAndView.addObject(
                    VerificationTokensConstants.MESSAGE_VIEW_ATTRIBUTE_KEY,
                    "Account deletion token handling not implemented yet"
                );
            }
            case EMAIL_VERIFICATION_TOKEN -> {
                verificationTokenMetadata = this.objectMapper.convertValue(
                    verificationToken.getMetadata(),
                    EmailVerificationTokenMetadataModel.class
                );

                if (
                    ((EmailVerificationTokenMetadataModel) verificationTokenMetadata).shouldVerifyUserAccount()
                )
                    this.authService.setUserAsVerified(user);

                modelAndView.setViewName(
                    VerificationTokensConstants.VERIFICATION_TOKEN_VIEW_NAME
                );
                modelAndView.addObject(
                    VerificationTokensConstants.IS_VALID_TOKEN_VIEW_ATTRIBUTE_KEY,
                    true
                );
                modelAndView.addObject(
                    VerificationTokensConstants.MESSAGE_VIEW_ATTRIBUTE_KEY,
                    "Your user account has been verified successfully"
                );
            }
            case RESET_PASSWORD_TOKEN -> {
                // TODO: Implement password reset token handling
                modelAndView.setStatus(HttpStatus.NOT_IMPLEMENTED);
                modelAndView.setViewName(
                    VerificationTokensConstants.VERIFICATION_TOKEN_VIEW_NAME
                );
                modelAndView.addObject(
                    VerificationTokensConstants.IS_VALID_TOKEN_VIEW_ATTRIBUTE_KEY,
                    false
                );
                modelAndView.addObject(
                    VerificationTokensConstants.MESSAGE_VIEW_ATTRIBUTE_KEY,
                    "Password reset token handling not implemented yet"
                );
            }
            default -> {
                VerificationTokenInterceptor.CONSOLE_LOGGER.error(
                    "Token handling for {} not implemented yet, redirecting...",
                    verificationTokenType
                );

                modelAndView.setStatus(HttpStatus.NOT_IMPLEMENTED);
                modelAndView.setViewName(
                    VerificationTokensConstants.VERIFICATION_TOKEN_VIEW_NAME
                );
                modelAndView.addObject(
                    VerificationTokensConstants.IS_VALID_TOKEN_VIEW_ATTRIBUTE_KEY,
                    false
                );
                modelAndView.addObject(
                    VerificationTokensConstants.MESSAGE_VIEW_ATTRIBUTE_KEY,
                    "Unknown Token Type: " + verificationTokenType
                );
            }
        }
    }

    @Override
    public boolean preHandle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Object handler
    ) {
        VerificationTokenInterceptor.CONSOLE_LOGGER.info(
            "Pre-intercepting token verification request..."
        );

        final UserEntity user = (UserEntity) request.getAttribute(
            UsersConstants.USER_REQUEST_ATTRIBUTE_KEY
        );
        final EVerificationTokenType verificationTokenType = (EVerificationTokenType) request.getAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_TYPE_REQUEST_ATTRIBUTE_KEY
        );

        if (
            user.getAuthorities().contains(
                new SimpleGrantedAuthority(EBasicRole.USER.toString())
            ) &&
            verificationTokenType == EVerificationTokenType.EMAIL_VERIFICATION_TOKEN
        ) {
            VerificationTokenInterceptor.CONSOLE_LOGGER.info(
                "{}'s account is already verified...",
                user.getUsername()
            );

            request.setAttribute(
                VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE_KEY,
                true
            );
        }

        request.setAttribute(
            VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE_KEY,
            false
        );

        return true;
    }
}
