package org.web3.services.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtVerifier {
    private final Algorithm algorithm = JwtConfig.algorithm;
    private final com.auth0.jwt.JWTVerifier verifier;

    public JwtVerifier(String issuer){
        this.verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }
}
