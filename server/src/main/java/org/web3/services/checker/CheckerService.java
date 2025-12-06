package org.web3.services.checker;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jws.HandlerChain;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import org.web3.models.Point;
import org.web3.models.User;
import org.web3.repository.PointRepository;
import org.web3.services.checker.DTOs.CheckRequest;
import org.web3.services.checker.DTOs.CheckResultResponse;
import org.web3.services.checker.plot.Checker;
import org.web3.services.checker.plot.CheckerFunction;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@WebService
@HandlerChain(file = "/handlers/BearerAuthHandler.xml")
public class CheckerService {
    @Resource private WebServiceContext webServiceContext;
    @Inject private PointRepository pointRepository;
    @Inject private CheckerFunction checker;

    private final Validator validator;

    public CheckerService() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }

    public CheckResultResponse check(
            @WebParam(name = "x") final float x,
            @WebParam(name = "y") final float y,
            @WebParam(name = "r") final float r
    ) {
        final CheckRequest request = new CheckRequest(x, y, r);
        validateRequest(request);

        final int userId = getTokenFromContext().getClaim("uid").asInt();

        final Point point = Point.builder()
                .x(x)
                .y(y)
                .r(r)
                .isHitted(checker.check(x,y,r))
                .timestamp(OffsetDateTime.now())
                .user(
                        User.builder().id(userId).build()
                )
                .build();
        pointRepository.save(point);

        return new CheckResultResponse(point.isHitted(), point.getTimestamp());
    }

    private DecodedJWT getTokenFromContext() {
        final Object tokenObj = webServiceContext.getMessageContext().get("token");
        if (!(tokenObj instanceof DecodedJWT token)) {
            throw new IllegalStateException("JWT token missing in context");
        }
        return token;
    }

    private void validateRequest(CheckRequest request) {
        final Set<ConstraintViolation<CheckRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Validation failed: " + errors);
        }
    }

}