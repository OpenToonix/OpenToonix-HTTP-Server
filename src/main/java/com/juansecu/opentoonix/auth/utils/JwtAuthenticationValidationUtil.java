package com.juansecu.opentoonix.auth.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.juansecu.opentoonix.auth.AuthService;
import com.juansecu.opentoonix.auth.models.AuthenticationDetails;
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;
import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationValidationUtil {
    private static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    private static final String AUTHENTICATION_HEADER_PREFIX = "Bearer ";
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(JwtAuthenticationValidationUtil.class);
    private static final String TOKEN_PATTERN = "^([0-9a-z_=]+)\\.([0-9a-z_=]+)\\.([0-9a-z_\\-+/=]+)$";

    private final JwtAdapter jwtAdapter;
    private final UserDetailsService userDetailsService;

    public AuthenticationDetails validateAuthentication(
        final HttpServletRequest request
    ) {
        String token;
        UserEntity user;
        String username;

        final Cookie authenticationCookie = this.getAuthenticationCookie(request);
        final String authenticationHeaderValue = request.getHeader(
            JwtAuthenticationValidationUtil.AUTHENTICATION_HEADER_NAME
        );
        final boolean isValidAuthenticationCookie = this.isValidToken(authenticationCookie);
        final boolean isValidAuthenticationHeaderValue = this.isValidToken(authenticationHeaderValue);

        if (
            !isValidAuthenticationCookie &&
            !isValidAuthenticationHeaderValue
        ) return null;

        if (isValidAuthenticationCookie) {
            token = Objects.requireNonNull(authenticationCookie).getValue();
        } else {
            token = authenticationHeaderValue.substring(
                JwtAuthenticationValidationUtil.AUTHENTICATION_HEADER_PREFIX.length()
            );
        }

        username = this.jwtAdapter.getSubject(token);
        user = (UserEntity) this.userDetailsService.loadUserByUsername(username);

        return new AuthenticationDetails(token, user);
    }

    private Cookie getAuthenticationCookie(final HttpServletRequest request) {
        Cookie authenticationCookie = null;

        final Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            JwtAuthenticationValidationUtil.CONSOLE_LOGGER.error("No cookies were found");
            return null;
        }

        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), AuthService.USER_AUTHENTICATION_COOKIE_NAME)) {
                authenticationCookie = cookie;
                break;
            }
        }

        if (authenticationCookie == null) {
            JwtAuthenticationValidationUtil.CONSOLE_LOGGER.error("Authentication cookie is not present");
            return null;
        }

        return authenticationCookie;
    }

    private boolean isValidToken(final Cookie authenticationCookie) {
        if (authenticationCookie == null) {
            JwtAuthenticationValidationUtil.CONSOLE_LOGGER.error(
                "Authentication cookie is not present in request"
            );

            return false;
        }

        final String token = authenticationCookie.getValue();
        final Pattern pattern = Pattern.compile(
            JwtAuthenticationValidationUtil.TOKEN_PATTERN,
            Pattern.CASE_INSENSITIVE
        );
        final Matcher matcher = pattern.matcher(token);

        JwtAuthenticationValidationUtil.CONSOLE_LOGGER.info("Validating authentication cookie value...");

        if(!matcher.find()) {
            JwtAuthenticationValidationUtil.CONSOLE_LOGGER.error(
                "Invalid JSON Web Token syntax"
            );

            return false;
        }

        return this.jwtAdapter.isValidJsonWebToken(token);
    }

    private boolean isValidToken(final String authenticationHeaderValue) {
        if (authenticationHeaderValue == null) {
            JwtAuthenticationValidationUtil.CONSOLE_LOGGER.error(
                "Authentication header is not present"
            );

            return false;
        }

        String token;

        final Pattern pattern = Pattern.compile(
            "^" +
                JwtAuthenticationValidationUtil.AUTHENTICATION_HEADER_PREFIX +
                JwtAuthenticationValidationUtil.TOKEN_PATTERN +
                "$",
            Pattern.CASE_INSENSITIVE
        );
        final Matcher matcher = pattern.matcher(authenticationHeaderValue);

        if(!matcher.find()) {
            JwtAuthenticationValidationUtil.CONSOLE_LOGGER.error(
                "Invalid authentication header value"
            );

            return false;
        }

        token = authenticationHeaderValue.substring(
            AUTHENTICATION_HEADER_PREFIX.length()
        );

        return this.jwtAdapter.isValidJsonWebToken(token);
    }
}
