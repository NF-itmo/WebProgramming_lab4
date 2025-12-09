package org.authService.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.authService.services.exceptions.ConflictException;
import org.authService.services.exceptions.UnauthorizedException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.authService.models.User;
import org.authService.repository.UserRepository;
import org.jwtProcessing.StrictJwtBuilderFabric;

@ApplicationScoped
public class AuthService {
    @Inject
    private UserRepository userRepository;

    private final StrictJwtBuilderFabric jwtBuilderFabric = new StrictJwtBuilderFabric("AuthService");

    public String login(final String login, final String password) throws UnauthorizedException {
        final User user = userRepository.getByUsername(login);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return jwtBuilderFabric.newTokenBuilder(user.getUsername())
                .addClaim("uid", user.getId())
                .build();
    }

    public String register(final String login, final String password) throws ConflictException {
        final User existingUser = userRepository.getByUsername(login);
        if (existingUser != null) {
            throw new ConflictException("User already exists");
        }

        final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        final User user = User.builder()
                .password(hashedPassword)
                .username(login)
                .build();
        userRepository.save(user);

        return jwtBuilderFabric.newTokenBuilder(user.getUsername())
                .addClaim("uid", user.getId())
                .build();
    }
}
