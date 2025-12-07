package org.geometryService.services.checker;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.geometryService.services.checker.DTOs.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.geometryService.models.Point;
import org.geometryService.models.User;
import org.geometryService.repository.PointRepository;
import org.geometryService.services.checker.DTOs.CheckRequest;
import org.geometryService.services.checker.DTOs.CheckResultResponse;
import org.geometryService.services.checker.plot.CheckerFunction;
import org.jwtProcessing.filter.JwtSecured;

import java.time.OffsetDateTime;


@Path("/checker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JwtSecured
public class CheckerService {
    @Inject
    private PointRepository pointRepository;
    @Inject
    private CheckerFunction checker;

    private static final Logger logger = LoggerFactory.getLogger(CheckerService.class);

    @POST
    @Path("/check")
    public Response check(
            @Context ContainerRequestContext requestContext,
            @Valid CheckRequest request) {
        try {
            DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");

            final int userId = token.getClaim("uid").asInt();

            final Point point = Point.builder()
                    .x(request.getX())
                    .y(request.getY())
                    .r(request.getR())
                    .isHitted(checker.check(request.getX(), request.getY(), request.getR()))
                    .timestamp(OffsetDateTime.now())
                    .user(User.builder().id(userId).build())
                    .build();
            
            pointRepository.save(point);
            
            logger.info("Point checked for user {}: ({}, {}, {}) = {}", 
                    userId, request.getX(), request.getY(), request.getR(), point.isHitted());

            return Response.ok(new CheckResultResponse(point.isHitted(), point.getTimestamp())).build();

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Check error: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("check failed"))
                    .build();
        }
    }
}