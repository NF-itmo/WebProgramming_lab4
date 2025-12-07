package org.historyService.services.history;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.historyService.services.history.DTOs.ErrorResponse;
import org.historyService.services.history.DTOs.GetHistoryRequestQueryParams;
import org.jwtProcessing.filter.JwtSecured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.historyService.models.Point;
import org.historyService.repository.PointRepository;
import org.historyService.services.history.DTOs.PointHistoryResponse;
import org.historyService.services.history.DTOs.PointsCountResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtSecured
public class HistoryService {
    @Inject
    private PointRepository pointRepository;

    private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);

    @GET
    public Response get(
            @Context ContainerRequestContext requestContext,
            @Valid @BeanParam GetHistoryRequestQueryParams queryParams
    ) {
        try {
            DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");

            final int userId = token.getClaim("uid").asInt();

            final List<Point> points = pointRepository.getByUserId(
                    userId,
                    queryParams.getLength(),
                    queryParams.getStart()
            );

            PointHistoryResponse[] responses = points.stream()
                    .map(p -> new PointHistoryResponse(
                            p.getX(),
                            p.getY(),
                            p.getR(),
                            p.isHitted(),
                            p.getTimestamp()
                    ))
                    .toArray(PointHistoryResponse[]::new);

            logger.debug("Retrieved {} points for user {}", responses.length, userId);
            return Response.ok(responses).build();

        } catch (Exception e) {
            logger.error("Error retrieving history: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve history"))
                    .build();
        }
    }

    @GET
    @Path("/count")
    public Response getTotal(@Context ContainerRequestContext requestContext) {
        try {
            DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");

            final int userId = token.getClaim("uid").asInt();
            final long cnt = pointRepository.getPointsCntByUserId(userId);

            logger.debug("Retrieved total count {} for user {}", cnt, userId);
            return Response.ok(new PointsCountResponse(cnt)).build();

        } catch (Exception e) {
            logger.error("Error retrieving count: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve count"))
                    .build();
        }
    }
}

