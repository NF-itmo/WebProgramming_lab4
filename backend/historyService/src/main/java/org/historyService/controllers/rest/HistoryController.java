package org.historyService.controllers.rest;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.historyService.controllers.rest.DTO.*;
import org.historyService.controllers.rest.DTO.history.GetCountRequestQueryParams;
import org.historyService.controllers.rest.DTO.history.GetHistoryRequestQueryParams;
import org.historyService.controllers.rest.DTO.history.PointHistoryResponse;
import org.historyService.controllers.rest.DTO.history.PointsCountResponse;
import org.historyService.models.Point;
import org.historyService.services.HistoryService;
import org.historyService.services.exceptions.UnauthorizedException;
import org.jwtProcessing.filter.JwtSecured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtSecured
public class HistoryController {
    @Inject
    private HistoryService historyService;

    private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @GET
    public Response get(
            @Context ContainerRequestContext requestContext,
            @Valid @BeanParam GetHistoryRequestQueryParams queryParams
    ) {
        try {
            final DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");

            final int userId = token.getClaim("uid").asInt();
            final int groupId = queryParams.getGroupId();

            final List<Point> points = historyService.get(
                    userId, groupId, queryParams.getStart(), queryParams.getLength());


            final PointHistoryResponse[] responses = points.stream()
                    .map(p -> new PointHistoryResponse(
                            p.getX(),
                            p.getY(),
                            p.getR(),
                            p.isHitted(),
                            p.getTimestamp()
                    ))
                    .toArray(PointHistoryResponse[]::new);

            logger.debug("Retrieved {} points for user {} in group {}", responses.length, userId, groupId);
            return Response.ok(responses).build();

        } catch (UnauthorizedException e){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Error retrieving history: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve history"))
                    .build();
        }
    }

    @GET
    @Path("/count")
    public Response getTotal(
            @Context ContainerRequestContext requestContext,
            @Valid @BeanParam GetCountRequestQueryParams queryParams
    ) {
        try {
            final DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");

            final int userId = token.getClaim("uid").asInt();
            final int groupId = queryParams.getGroupId();

            final long cnt = historyService.getTotal(userId, groupId);

            logger.debug("Retrieved total count {} for user {} in group {}", cnt, userId, groupId);
            return Response.ok(new PointsCountResponse(cnt)).build();
        } catch (UnauthorizedException e){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            logger.error("Error retrieving count: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve count"))
                    .build();
        }
    }
}
