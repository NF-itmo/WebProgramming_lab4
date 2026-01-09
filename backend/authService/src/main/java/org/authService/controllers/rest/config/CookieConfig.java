package org.authService.controllers.rest.config;

import jakarta.ws.rs.core.NewCookie.SameSite;

public class CookieConfig {
    public static final String COOKIE_NAME = "auth_token";
    public static final int MAX_AGE = 3600 * 24 * 7;
    public static final boolean HTTP_ONLY = true;
    public static final boolean SECURE = false; // TODO: change to true
    public static final SameSite SAME_SITE = SameSite.STRICT;
}
