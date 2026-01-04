package org.jwtProcessing.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

public class JwtSecurityContext implements SecurityContext {
    private static final String AUTH_SCHEME = "Bearer";

    private final JwtPrincipal principal;
    private final boolean secure;

    public JwtSecurityContext(DecodedJWT decodedToken, boolean secure) {
        this.principal = new JwtPrincipal(decodedToken);
        this.secure = secure;
    }

    @Override
    public JwtPrincipal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return AUTH_SCHEME;
    }
}
