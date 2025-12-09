package org.authService.controllers.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.authService.services.AuthService;
import org.authService.controllers.rest.DTO.AuthRequest;
import org.authService.controllers.rest.DTO.ErrorResponse;
import org.authService.controllers.rest.DTO.TokenResponse;
import org.authService.services.exceptions.ConflictException;
import org.authService.services.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequest request) {
        try {
            String token = authService.login(request.getLogin(), request.getPassword());
            return Response.ok(new TokenResponse(token)).build();
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

            logger.info("Successful registration for user: {}", request.getLogin());
            return Response.status(Response.Status.CREATED)
                    .entity(new TokenResponse(token))
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
}
