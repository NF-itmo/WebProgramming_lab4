package org.authService.controllers.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.authService.controllers.rest.config.CookieConfig;
import org.authService.services.AuthService;
import org.authService.controllers.rest.DTO.AuthRequest;
import org.authService.controllers.rest.DTO.ErrorResponse;
import org.authService.controllers.rest.DTO.AuthResponse;
import org.authService.services.exceptions.ConflictException;
import org.authService.services.exceptions.UnauthorizedException;
import org.jwtProcessing.filter.JwtSecured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequest request) {
        try {
            String token = authService.login(request.getLogin(), request.getPassword());
            NewCookie authCookie = createAuthCookie(token);

            return Response.ok(new AuthResponse("Login successful"))
                    .cookie(authCookie)
                    .build();
        } catch (UnauthorizedException e) {
            logger.warn("Failed login attempt for user: {}", request.getLogin());
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Invalid credentials"))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Login error: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Authentication failed"))
                    .build();
        }
    }

    @POST
    @Path("/register")
    public Response register(@Valid AuthRequest request) {
        try {
            final String token = authService.register(request.getLogin(), request.getPassword());
            NewCookie authCookie = createAuthCookie(token);

            logger.info("Successful registration for user: {}", request.getLogin());
            return Response.status(Response.Status.CREATED)
                    .entity(new AuthResponse("Register successful"))
                    .cookie(authCookie)
                    .build();
        } catch (ConflictException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("User already exists"))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Registration error: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Registration failed"))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        NewCookie logoutCookie = new NewCookie.Builder(CookieConfig.COOKIE_NAME)
                .value("")
                .maxAge(0)
                .httpOnly(CookieConfig.HTTP_ONLY)
                .secure(CookieConfig.SECURE)
                .sameSite(CookieConfig.SAME_SITE)
                .path("/")
                .build();

        return Response.ok(new AuthResponse("Logout successful"))
                .cookie(logoutCookie)
                .build();
    }

    @GET
    @Path("/validate")
    @JwtSecured
    public Response validate() {
        return Response.ok(new AuthResponse("Token is valid")).build();
    }

    private NewCookie createAuthCookie(String token) {
        return new NewCookie.Builder(CookieConfig.COOKIE_NAME)
                .value("Bearer " + token)
                .maxAge(CookieConfig.MAX_AGE)
                .httpOnly(CookieConfig.HTTP_ONLY)
                .secure(CookieConfig.SECURE)
                .sameSite(CookieConfig.SAME_SITE)
                .path("/")
                .build();
    }
}
