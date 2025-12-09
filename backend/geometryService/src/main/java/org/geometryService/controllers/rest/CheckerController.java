package org.geometryService.controllers.rest;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.geometryService.controllers.rest.DTO.CheckRequest;
import org.geometryService.controllers.rest.DTO.CheckResultResponse;
import org.geometryService.controllers.rest.DTO.ErrorResponse;
import org.geometryService.services.CheckerService;
import org.jwtProcessing.filter.JwtSecured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;


@Path("/geometry")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JwtSecured
public class CheckerController {
    @Inject
    private CheckerService checkerService;

    private static final Logger logger = LoggerFactory.getLogger(CheckerService.class);

    @POST
    @Path("/check")
    public Response check(
            @Context ContainerRequestContext requestContext,
            @Valid CheckRequest request
    ) {
        try {
            DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");
            final int userId = token.getClaim("uid").asInt();

            final OffsetDateTime timestamp = OffsetDateTime.now();

            boolean isHitted = checkerService.check(
                    userId,
                    request.getGroupId(),
                    request.getX(),
                    request.getY(),
                    request.getR()
            );

            logger.info("Point checked for user {}: ({}, {}, {}) = {}",
                    userId, request.getX(), request.getY(), request.getR(), isHitted);

            return Response.ok(new CheckResultResponse(isHitted, timestamp)).build();

        } catch (IllegalArgumentException e) {
            logger.warn("Validation error: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Check error: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Check failed"))
                    .build();
        }
    }
}
