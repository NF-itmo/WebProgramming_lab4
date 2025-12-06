package org.web3.services.jwt;

import com.auth0.jwt.JWT;

import static org.web3.services.jwt.JwtConfig.JWT_EXPIRATION_MS;

public class StrictJwtBuilderFabric {
    private final String issuer;
    private int expirationDelta = JWT_EXPIRATION_MS;

    public StrictJwtBuilderFabric(String issuer, int expirationDelta) {
        this.issuer = issuer;
        this.expirationDelta = expirationDelta;
    }
    public StrictJwtBuilderFabric(String issuer) {
        this.issuer = issuer;
    }

    public JwtBuilder newTokenBuilder(String subject) {
        return new JwtBuilder(
                JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(subject),
                expirationDelta
        );
    }
}
