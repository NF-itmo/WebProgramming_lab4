package org.jwtProcessing;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Verifies JWT tokens signed by any service using the shared secret.
 */
public class JwtVerifier {
    private final String expectedIssuer;

    public JwtVerifier(String expectedIssuer) {
        this.expectedIssuer = expectedIssuer;
    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        return JWT.require(JwtConfig.ALGORITHM)
                .withIssuer(expectedIssuer)
                .build()
                .verify(token);
    }
}
