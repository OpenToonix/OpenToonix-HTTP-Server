package com.juansecu.opentoonix.verificationtokens.filters;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.juansecu.opentoonix.auth.models.AuthenticationDetails;
import com.juansecu.opentoonix.auth.utils.JwtAuthenticationValidationUtil;
import com.juansecu.opentoonix.users.UsersApiService;
import com.juansecu.opentoonix.users.UsersConstants;
import com.juansecu.opentoonix.users.models.entities.UserEntity;
import com.juansecu.opentoonix.verificationtokens.VerificationTokensConstants;
import com.juansecu.opentoonix.verificationtokens.VerificationTokensApiService;
import com.juansecu.opentoonix.verificationtokens.enums.EVerificationTokenType;
import com.juansecu.opentoonix.verificationtokens.models.entities.VerificationTokenEntity;

@Component
@RequiredArgsConstructor
public class VerificationTokenFinderFilter extends OncePerRequestFilter {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(VerificationTokenFinderFilter.class);

    private final JwtAuthenticationValidationUtil jwtAuthenticationValidationUtil;
    private final UsersApiService usersApiService;
    private final VerificationTokensApiService verificationTokensApiService;

    @Override
    protected void doFilterInternal(
        @NonNull final HttpServletRequest request,
        @NonNull final HttpServletResponse response,
        @NonNull final FilterChain filterChain
    ) throws IOException, ServletException {
        Optional<UserEntity> user;
        int userId;
        Optional<VerificationTokenEntity> verificationToken;
        EVerificationTokenType verificationTokenType;

        final AuthenticationDetails authenticationDetails = this.jwtAuthenticationValidationUtil.validateAuthentication(
            request
        );
        final String encryptedToken = request.getParameter("token");
        final boolean isUserAuthenticated = authenticationDetails.isUserAuthenticated();
        final String tokenType = request.getParameter(
            VerificationTokensConstants.TOKEN_TYPE_REQUEST_PARAMETER
        );
        final String userIdParameter = request.getParameter("userId");

        if (encryptedToken == null || tokenType == null || userIdParameter == null) {
            VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                "Missing required request parameters"
            );

            return;
        } else if (!userIdParameter.matches("\\d+")) {
            VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                "The user ID is not a valid integer"
            );

            return;
        }

        userId = Integer.parseInt(userIdParameter);

        VerificationTokenFinderFilter.CONSOLE_LOGGER.info(
            "Verifying if user is authenticated and if the user ID matches with the token..."
        );

        if (isUserAuthenticated) {
            if (authenticationDetails.user().getUserId() != userId) {
                VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                    "The user ID does not match with the authenticated user account"
                );

                return;
            }

            user = Optional.of(authenticationDetails.user());
        } else user = this.usersApiService.findById(userId);

        if (user.isEmpty()) {
            VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                "The user does not exist"
            );

            return;
        }

        try {
            verificationTokenType = EVerificationTokenType.valueOf(tokenType);
        } catch (final IllegalArgumentException illegalArgumentException) {
            VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                "The verification token type is not valid"
            );

            return;
        }

        verificationToken = this.verificationTokensApiService.getVerificationToken(
            encryptedToken,
            verificationTokenType,
            user.get()
        );

        if (verificationToken.isEmpty()) {
            VerificationTokenFinderFilter.CONSOLE_LOGGER.error(
                "Verification token not found"
            );

            return;
        }

        request.setAttribute(
            VerificationTokensConstants.IS_INVALID_TOKEN_REQUEST_ATTRIBUTE,
            false
        );
        request.setAttribute(UsersConstants.USER_REQUEST_ATTRIBUTE, user.get());
        request.setAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_REQUEST_ATTRIBUTE,
            verificationToken.get()
        );
        request.setAttribute(
            VerificationTokensConstants.VERIFICATION_TOKEN_TYPE_REQUEST_ATTRIBUTE,
            verificationTokenType
        );

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(
        @NonNull final HttpServletRequest request
    ) {
        final List<String> excludedPaths = List.of(
            "/verification-tokens/verify"
        );
        final AntPathMatcher pathMatcher = new AntPathMatcher();

        // Check if the request path matches any of the excluded paths
        for (final String excludedPath : excludedPaths) {
            if (!pathMatcher.match(excludedPath, request.getServletPath()))
                return true;
        }

        return false;
    }
}
