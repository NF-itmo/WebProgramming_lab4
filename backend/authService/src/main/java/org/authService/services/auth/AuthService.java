package org.authService.services.auth;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.authService.models.User;
import org.authService.repository.UserRepository;
import org.authService.services.auth.DTOs.AuthRequest;
import org.authService.services.auth.DTOs.ErrorResponse;
import org.authService.services.auth.DTOs.TokenResponse;
import org.jwtProcessing.StrictJwtBuilderFabric;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthService {
    @Inject
    private UserRepository userRepository;

    private final StrictJwtBuilderFabric jwtBuilderFabric = new StrictJwtBuilderFabric("AuthService");
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequest request) {
        try {
            final User user = userRepository.getByUsername(request.getLogin());
            if (user == null || !BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                logger.warn("Failed login attempt for user: {}", request.getLogin());
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Invalid credentials"))
                        .build();
            }

            String token = jwtBuilderFabric.newTokenBuilder(user.getUsername())
                    .addClaim("uid", user.getId())
                    .build();

            logger.info("Successful login for user: {}", request.getLogin());
            return Response.ok(new TokenResponse(token)).build();

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
            final User existingUser = userRepository.getByUsername(request.getLogin());
            if (existingUser != null) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ErrorResponse("User already exists"))
                        .build();
            }

            final String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
            final User user = User.builder()
                    .password(hashedPassword)
                    .username(request.getLogin())
                    .build();
            userRepository.save(user);

            final String token = jwtBuilderFabric.newTokenBuilder(user.getUsername())
                    .addClaim("uid", user.getId())
                    .build();

            logger.info("Successful registration for user: {}", request.getLogin());
            return Response.status(Response.Status.CREATED)
                    .entity(new TokenResponse(token))
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
