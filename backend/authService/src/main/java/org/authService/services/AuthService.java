package org.authService.services;

import org.authService.models.User;
import org.authService.repository.UserRepository;
import org.authService.services.saga.RegistrationSagaOrchestrator;
import org.authService.services.exceptions.ConflictException;
import org.authService.services.exceptions.UnauthorizedException;
import org.jwtProcessing.StrictJwtBuilderFabric;
import org.springframework.security.crypto.bcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {
    @Inject
    private UserRepository userRepository;

    @Inject
    private RegistrationSagaOrchestrator registrationSagaOrchestrator;

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
        final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        final User user = registrationSagaOrchestrator.executeRegistrationSaga(login, hashedPassword);

        return jwtBuilderFabric.newTokenBuilder(user.getUsername())
                .addClaim("uid", user.getId())
                .build();
    }
}
