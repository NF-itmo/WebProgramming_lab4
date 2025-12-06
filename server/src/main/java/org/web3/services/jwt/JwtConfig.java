package org.web3.services.jwt;

import com.auth0.jwt.algorithms.Algorithm;

class JwtConfig {
    private static final String secret = System.getenv().get("JWT_SECRET");
    public static final Algorithm algorithm = Algorithm.HMAC256(secret);
    public static final int JWT_EXPIRATION_MS = Integer.parseInt(System.getenv().getOrDefault("JWT_EXPIRATION_MS", "3600000"));
}
