package com.juansecu.opentoonix.shared.adapters;

/* --- Java modules --- */
import java.util.Date;

/* --- Third-party modules --- */
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * Contains all related tasks to JSON Web Tokens.
 */
public class JwtAdapter {
    private static final Algorithm JWT_ALGORITHM = Algorithm.HMAC256("${JWT_SECRET}");

    /**
     * Generates and Signs a JSON Web Token with the given information.
     *
     * @param subject The subject to identify the web token
     * @param issuer The issuer of the web token
     * @param expiresAt The time the token expires
     * @return String
     */
    public String generateJsonWebToken(
        final String subject,
        final String issuer,
        final Date expiresAt
    ) {
        return JWT.create()
            .withSubject(subject)
            .withIssuer(issuer)
            .withExpiresAt(expiresAt)
            .sign(JwtAdapter.JWT_ALGORITHM);
    }
}
