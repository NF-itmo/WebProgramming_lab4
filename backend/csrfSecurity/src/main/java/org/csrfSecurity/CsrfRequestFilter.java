package org.csrfSecurity;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@CsrfSecured
@Priority(Priorities.AUTHENTICATION)
public class CsrfRequestFilter implements ContainerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(CsrfRequestFilter.class);
    private static final String CSRF_HEADER = "X-CSRF-Token";
    private static final String COOKIE_NAME = "csrf_token";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String csrfHeader = requestContext.getHeaderString(CSRF_HEADER);
        Cookie csrfCookie = requestContext.getCookies().get(COOKIE_NAME);

        if (csrfCookie == null || csrfHeader == null || !csrfHeader.equals(csrfCookie.getValue())) {
            logger.warn("Missing or invalid CSRF protection header or cookie: {} {}", CSRF_HEADER, COOKIE_NAME);
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"message\": \"Missing or invalid CSRF protection header or cookie\"}")
                    .build());
        }
    }
}
