package com.juansecu.opentoonix.core.filters;

/* --- Java modules --- */
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* --- Javax modules --- */
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* --- Third-party modules --- */
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/* --- Application modules --- */
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;
import com.juansecu.opentoonix.shared.dtos.responses.BaseResDto;
import com.juansecu.opentoonix.user.UserService;
import com.juansecu.opentoonix.user.enums.EAuthenticationError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);
    private static final String TOKEN_PATTERN = "^([0-9a-z_=]+)\\.([0-9a-z_=]+)\\.([0-9a-z_\\-+/=]+)$";

    private final List<AntPathRequestMatcher> excludedMatchers;
    private final JwtAdapter jwtAdapter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final FilterChain filterChain
    ) throws IOException, ServletException {
        JwtAuthenticationFilter.CONSOLE_LOGGER.info("Validating authentication cookie value...");

        try {
            final Cookie authenticationCookie = this.getAuthenticationCookie(request);
            final boolean isValidToken = this.isValidToken(Objects.requireNonNull(authenticationCookie));

            if (!isValidToken) {
                JwtAuthenticationFilter.CONSOLE_LOGGER.error("Invalid authentication cookie value");

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
        } catch (NullPointerException nullPointerException) {
            JwtAuthenticationFilter.CONSOLE_LOGGER.error("Authentication cookie is not present in request");

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            objectMapper.writeValue(
                response.getWriter(),
                new BaseResDto<>(
                    EAuthenticationError.NO_AUTHENTICATION_COOKIE,
                    false
                )
            );

            return;
        }

        JwtAuthenticationFilter.CONSOLE_LOGGER.info(
            "JSON Web Token validated successfully! Continuing workflow of request"
        );

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return this.excludedMatchers
            .stream()
            .anyMatch((AntPathRequestMatcher matcher) -> matcher.matches(request));
    }

    private Cookie getAuthenticationCookie(final HttpServletRequest request) {
        Cookie authenticationCookie = null;

        final Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            JwtAuthenticationFilter.CONSOLE_LOGGER.error("No cookies were found");
            return null;
        }

        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), UserService.USER_AUTHENTICATION_COOKIE_NAME)) {
                authenticationCookie = cookie;
                break;
            }
        }

        if (authenticationCookie == null) {
            JwtAuthenticationFilter.CONSOLE_LOGGER.error("Authentication cookie is not present");
            return null;
        }

        return authenticationCookie;
    }

    private boolean isValidToken(final Cookie authenticationCookie) {
        final Pattern pattern = Pattern.compile(JwtAuthenticationFilter.TOKEN_PATTERN, Pattern.CASE_INSENSITIVE);
        final String token = authenticationCookie.getValue();
        final Matcher matcher = pattern.matcher(token);

        if(!matcher.find()) {
            JwtAuthenticationFilter.CONSOLE_LOGGER.error("Invalid JSON Web Token syntax");
            return false;
        }

        return this.jwtAdapter.isValidJsonWebToken(token);
    }
}
