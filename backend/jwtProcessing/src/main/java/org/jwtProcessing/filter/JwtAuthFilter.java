package org.jwtProcessing.filter;

import java.io.IOException;

import org.jwtProcessing.JwtVerifier;
import org.jwtProcessing.security.JwtSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@JwtSecured
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtVerifier jwtVerifier;

    public JwtAuthFilter() {
        String issuer = System.getenv().get("JWT_ISSUER");
        if (issuer == null) throw new RuntimeException("JWT_ISSUER environment variable is not set");
        this.jwtVerifier = new JwtVerifier(issuer);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(AUTHORIZATION_HEADER);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            logger.warn("Missing or invalid Authorization header");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Missing or invalid authorization header\"}")
                    .build());
            return;
        }

        try {
            String token = authHeader.substring(BEARER_PREFIX.length()).trim();
            DecodedJWT decodedToken = jwtVerifier.verify(token);

            JwtSecurityContext securityContext = new JwtSecurityContext(
                    decodedToken,
                    requestContext.getSecurityContext().isSecure()
            );
            requestContext.setSecurityContext(securityContext);
            
            logger.debug("JWT token verified successfully for user: {}", decodedToken.getSubject());
        } catch (JWTVerificationException e) {
            logger.warn("JWT verification failed: {}", e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Invalid or expired token\"}")
                    .build());
        }
    }
}
