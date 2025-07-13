package com.juansecu.opentoonix.auth.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.juansecu.opentoonix.auth.enums.EAuthenticationError;
import com.juansecu.opentoonix.auth.models.AuthenticationDetails;
import com.juansecu.opentoonix.auth.utils.JwtAuthenticationValidationUtil;
import com.juansecu.opentoonix.shared.dtos.responses.BaseResDto;
import com.juansecu.opentoonix.users.models.entities.UserEntity;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);

    private final JwtAuthenticationValidationUtil jwtAuthenticationValidationUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        @NonNull final HttpServletRequest request,
        @NonNull final HttpServletResponse response,
        @NonNull final FilterChain filterChain
    ) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authenticationToken;
        UserEntity user;
        String username;

        final AuthenticationDetails authenticationDetails = this.jwtAuthenticationValidationUtil.validateAuthentication(
            request
        );

        if (authenticationDetails == null) {
            JwtAuthenticationFilter.CONSOLE_LOGGER.error("Invalid authentication");

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            objectMapper.writeValue(
                response.getWriter(),
                new BaseResDto<>(
                    EAuthenticationError.INVALID_AUTHENTICATION_COOKIE,
                    false
                )
            );

            return;
        }

        username = Objects.requireNonNull(authenticationDetails.user()).getUsername();

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            JwtAuthenticationFilter.CONSOLE_LOGGER.info(
                "User {} is already authenticated",
                username
            );

            filterChain.doFilter(request, response);

            return;
        }

        user = authenticationDetails.user();

        JwtAuthenticationFilter.CONSOLE_LOGGER.info(
            "Authenticating user {}...",
            username
        );

        authenticationToken = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull final HttpServletRequest request) {
        final List<String> excludedPaths = Arrays.asList(
            "/api-docs/**",
            "/auth/**",
            "/docs",
            "/swagger-ui/**",
            "/verification-tokens/verify"
        );
        final AntPathMatcher pathMatcher = new AntPathMatcher();

        // Check if the request path matches any of the excluded paths
        for (final String excludedPath : excludedPaths) {
            if (pathMatcher.match(excludedPath, request.getServletPath()))
                return true;
        }

        return false;
    }
}
