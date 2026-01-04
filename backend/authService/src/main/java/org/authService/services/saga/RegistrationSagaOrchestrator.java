package org.authService.services.saga;

import org.authService.grpc.GroupsServiceClient;
import org.authService.models.User;
import org.authService.repository.UserRepository;
import org.authService.services.exceptions.ConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class RegistrationSagaOrchestrator {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationSagaOrchestrator.class);
    private static final String DEFAULT_GROUP_NAME = "default";

    @Inject
    private UserRepository userRepository;

    @Inject
    private GroupsServiceClient groupsServiceClient;

    public User executeRegistrationSaga(String username, String hashedPassword) throws ConflictException {
        logger.info("Starting registration SAGA for user: {}", username);

        User user = createUserStep(username, hashedPassword);
        logger.info("User creation step completed, userId: {}", user.getId());

        boolean groupCreated = groupsServiceClient.createDefaultGroup(user.getId(), DEFAULT_GROUP_NAME);

        if (!groupCreated) {
            logger.error("Default group creation failed for user: {}, executing compensation", username);

            compensateUserCreation(user.getId(), username);
            
            throw new ConflictException("Failed to create default group during registration");
        }

        logger.info("Registration SAGA completed successfully for user: {}", username);
        return user;
    }

    private User createUserStep(String username, String hashedPassword) throws ConflictException {
        final User existingUser = userRepository.getByUsername(username);
        if (existingUser != null) {
            throw new ConflictException("User already exists");
        }

        final User user = User.builder()
                .password(hashedPassword)
                .username(username)
                .build();
        
        userRepository.save(user);
        return user;
    }

    private void compensateUserCreation(int userId, String username) {
        try {
            userRepository.deleteById(userId);
            logger.info("User compensation completed: user {} deleted", username);
        } catch (Exception e) {
            logger.error("Failed to compensate user creation for user {}: {}", username, e.getMessage(), e);
        }
    }
}
