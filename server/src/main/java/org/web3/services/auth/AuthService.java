package org.web3.services.auth;

import jakarta.inject.Inject;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.web3.models.User;
import org.web3.repository.UserRepository;
import org.web3.services.auth.DTOs.TokenResponse;
import org.web3.services.jwt.StrictJwtBuilderFabric;

@WebService
public class AuthService {
    @Inject private UserRepository userRepository;

    private final StrictJwtBuilderFabric jwtBuilderFabric = new StrictJwtBuilderFabric("AuthService");
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public TokenResponse login(
            @WebParam(name = "login") String login,
            @WebParam(name = "password") String password
    ) {
        if (login == null || login.trim().isEmpty() || password == null || password.isEmpty()) {
            logger.warn("Login attempt with empty credentials");
            throw new IllegalArgumentException("Login and password are required");
        }

        try {
            final User user = userRepository.getByUsername(login);
            if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
                logger.warn("Failed login attempt for user: {}", login);
                throw new IllegalArgumentException("Invalid credentials");
            }

            String token = jwtBuilderFabric.newTokenBuilder(user.getUsername())
                    .addKey("uid", user.getId())
                    .buildToken();

            logger.info("Successful login for user: {}", login);
            return new TokenResponse(token);

        } catch (Exception e) {
            logger.error("Login error for user: {}", login, e);
            throw new IllegalArgumentException("Authentication failed");
        }
    }

    public TokenResponse register(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password
    ) {
        if (login == null || login.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Login and password are required");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        try {
            final User existingUser = userRepository.getByUsername(login);
            if (existingUser != null) {
                throw new IllegalArgumentException("User already exists");
            }

            final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            final User user = User.builder()
                    .password(hashedPassword)
                    .username(login)
                    .build();
            userRepository.save(user);

            final String token = jwtBuilderFabric.newTokenBuilder(user.getUsername())
                    .addKey("uid", user.getId())
                    .buildToken();

            logger.info("Successful registration for user: {}", login);
            return new TokenResponse(token);

        } catch (Exception e) {
            logger.error("Registration error for user: {}", login, e);
            throw new IllegalArgumentException("Registration failed");
        }
    }
}
