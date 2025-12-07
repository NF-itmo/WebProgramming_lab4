package org.jwtProcessing;

import com.auth0.jwt.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtBuilder {
    private final String issuer;
    private final Map<String, Object> customClaims = new HashMap<>();
    private final String subject;
    private long expirationSeconds = JwtConfig.JWT_EXPIRATION_MS;

    public JwtBuilder(String issuer, String subject) {
        this.issuer = issuer;
        this.subject = subject;
    }

    public JwtBuilder addClaim(String key, Object value) {
        customClaims.put(key, value);
        return this;
    }
    public JwtBuilder withExpiration(long seconds) {
        this.expirationSeconds = seconds;
        return this;
    }
    public String build() {
        com.auth0.jwt.JWTCreator.Builder builder = JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + (expirationSeconds * 1000)));

        for (Map.Entry<String, Object> entry : customClaims.entrySet()) {
            if (entry.getValue() instanceof String) {
                builder = builder.withClaim(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Integer) {
                builder = builder.withClaim(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Long) {
                builder = builder.withClaim(entry.getKey(), (Long) entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                builder = builder.withClaim(entry.getKey(), (Boolean) entry.getValue());
            } else {
                builder = builder.withClaim(entry.getKey(), entry.getValue().toString());
            }
        }

        return builder.sign(org.jwtProcessing.JwtConfig.ALGORITHM);
    }
}
