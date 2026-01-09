package org.csrfSecurity;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.ext.Provider;

import java.util.UUID;

@Provider
@CsrfSecured
@Priority(Priorities.AUTHENTICATION)
public class CsrfResponseFilter implements ContainerResponseFilter {
    private static final String COOKIE_NAME = "csrf_token";

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) {
        String newToken = UUID.randomUUID().toString();

        NewCookie csrfCookie = new NewCookie.Builder(COOKIE_NAME)
                .value(newToken)
                .path("/")
                .httpOnly(false)
                .sameSite(NewCookie.SameSite.STRICT)
                .secure(true)
                .build();

        res.getHeaders().add("Set-Cookie", csrfCookie);
    }
}
