package org.jwtProcessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrictJwtBuilderFabric {
    private static final Logger logger = LoggerFactory.getLogger(StrictJwtBuilderFabric.class);
    private final String issuer;

    public StrictJwtBuilderFabric(String issuer) {
        this.issuer = issuer;
    }

    public JwtBuilder newTokenBuilder(String subject) {
        return new JwtBuilder(issuer, subject);
    }

    public String createToken(String subject) {
        return newTokenBuilder(subject).build();
    }

    public String createToken(String subject, long expirationSeconds) {
        return newTokenBuilder(subject)
                .withExpiration(expirationSeconds)
                .build();
    }
}
