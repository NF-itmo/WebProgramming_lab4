package org.geometryService.controllers.rest;

import java.time.OffsetDateTime;

import jakarta.ws.rs.*;
import org.geometryService.controllers.rest.DTO.CheckRequest;
import org.geometryService.controllers.rest.DTO.CheckResultResponse;
import org.geometryService.controllers.rest.DTO.ErrorResponse;
import org.geometryService.services.CheckerService;
import org.jwtProcessing.filter.JwtSecured;
import org.jwtProcessing.security.JwtPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@JwtSecured
public class CheckerController {
    @Inject
    private CheckerService checkerService;

    private static final Logger logger = LoggerFactory.getLogger(CheckerController.class);

    @POST
    @Path("check")
    public Response check(
            @Context SecurityContext securityContext,
            @Valid CheckRequest request
    ) {
        try {
            final int userId = ((JwtPrincipal) securityContext.getUserPrincipal()).getUid();

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
