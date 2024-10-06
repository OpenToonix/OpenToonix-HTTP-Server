package com.juansecu.opentoonix.verificationtokens.filters;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.juansecu.opentoonix.auth.AuthConstants;
import com.juansecu.opentoonix.auth.models.AuthenticationDetails;
import com.juansecu.opentoonix.auth.utils.JwtAuthenticationValidationUtil;
import com.juansecu.opentoonix.users.UsersConstants;
import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.verificationtokens.VerificationTokensConstants;
import com.juansecu.opentoonix.verificationtokens.VerificationTokensService;
import com.juansecu.opentoonix.verificationtokens.dtos.responses.IsValidTokenResDto;
import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;
import com.juansecu.opentoonix.verificationtokens.models.entities.VerificationTokenEntity;

@Component
@RequiredArgsConstructor
public class VerificationTokenFinderFilter extends OncePerRequestFilter {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(VerificationTokenFinderFilter.class);

    private final JwtAuthenticationValidationUtil jwtAuthenticationValidationUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    private final VerificationTokensService verificationTokensService;

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain
    ) throws IOException, ServletException {
        UserEntity user;
        VerificationTokenEntity verificationToken;
        EVerificationTokenType verificationTokenType;

        final AuthenticationDetails authenticationDetails = this.jwtAuthenticationValidationUtil.validateAuthentication(
            request
        );
        final String encryptedToken = request.getParameter(
            VerificationTokensConstants.TOKEN_REQUEST_PARAMETER_KEY
        );
        final boolean isUserAuthenticated = authenticationDetails != null;
        final String username = request.getParameter(
            UsersConstants.USERNAME_REQUEST_PARAMETER_KEY
        );
        final IsValidTokenResDto defaultResponse = new IsValidTokenResDto(
            false,
            isUserAuthenticated
        );

        request.setAttribute(
            AuthConstants.IS_USER_AUTHENTICATED_REQUEST_ATTRIBUTE_KEY,
            isUserAuthenticated
        );

        if (isUserAuthenticated) {
            if (!authenticationDetails.user().getUsername().equals(username)) {
                VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                    "The username does not match with the authenticated user"
                );

                this.objectMapper.writeValue(
                    response.getWriter(),
                    defaultResponse
                );

                return;
            }

            user = authenticationDetails.user();
        } else
            user = (UserEntity) this.userDetailsService.loadUserByUsername(
                username
            );

        try {
            verificationTokenType = EVerificationTokenType.valueOf(
                request.getParameter(
                    VerificationTokensConstants.TOKEN_TYPE_REQUEST_PARAMETER_KEY
                )
            );
        } catch (final IllegalArgumentException illegalArgumentException) {
            VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                "The verification token type is not valid"
            );

            this.objectMapper.writeValue(response.getWriter(), defaultResponse);

            return;
        }

        verificationToken = this.verificationTokensService.getVerificationToken(
            encryptedToken,
            verificationTokenType,
            user
        );

        if (verificationToken == null) {
            VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                "Verification token not found"
            );

            this.objectMapper.writeValue(response.getWriter(), defaultResponse);

            return;
        }

        request.setAttribute(UsersConstants.USER_REQUEST_ATTRIBUTE_KEY, user);
        request.setAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_REQUEST_ATTRIBUTE_KEY,
            verificationToken
        );
        request.setAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_TYPE_REQUEST_ATTRIBUTE_KEY,
            verificationTokenType
        );

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        final AntPathRequestMatcher[] excludedMatchers = {
            new AntPathRequestMatcher("/verification-tokens/verify")
        };

        return Arrays
            .stream(excludedMatchers)
            .noneMatch(
                (AntPathRequestMatcher matcher) -> matcher.matches(request)
            );
    }
}
