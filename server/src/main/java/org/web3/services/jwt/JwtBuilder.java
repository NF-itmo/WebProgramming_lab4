package org.web3.services.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtBuilder {
    private final Algorithm algorithm = JwtConfig.algorithm;
    private final JWTCreator.Builder tokenBuilder;
    private final int expirationDelta;

    JwtBuilder(JWTCreator.Builder tokenBuilder, int expirationDelta) {
        this.tokenBuilder = tokenBuilder;
        this.expirationDelta = expirationDelta;
    }

    public JwtBuilder addKey(String key, int value) {
        tokenBuilder.withClaim(key, value);
        return this;
    }

    public String buildToken() {
        return tokenBuilder.withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationDelta))
                .sign(algorithm);
    }
}
