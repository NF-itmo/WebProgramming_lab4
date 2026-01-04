package org.historyService.controllers.rest;

import java.util.List;

import org.historyService.controllers.rest.DTO.ErrorResponse;
import org.historyService.controllers.rest.DTO.history.GetCountRequestQueryParams;
import org.historyService.controllers.rest.DTO.history.GetHistoryRequestQueryParams;
import org.historyService.controllers.rest.DTO.history.PointHistoryResponse;
import org.historyService.controllers.rest.DTO.history.PointsCountResponse;
import org.historyService.models.Point;
import org.historyService.services.HistoryService;
import org.historyService.services.exceptions.UnauthorizedException;
import org.jwtProcessing.filter.JwtSecured;
import org.jwtProcessing.security.JwtPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtSecured
public class HistoryController {
    @Inject
    private HistoryService historyService;

    private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

    @GET
    public Response get(
            @Context SecurityContext securityContext,
            @Valid @BeanParam GetHistoryRequestQueryParams queryParams
    ) {
        try {
            final int userId = ((JwtPrincipal) securityContext.getUserPrincipal()).getUid();
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

            logger.info("Retrieved {} points for user {} in group {}", responses.length, userId, groupId);
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
            @Context SecurityContext securityContext,
            @Valid @BeanParam GetCountRequestQueryParams queryParams
    ) {
        try {
            final int userId = ((JwtPrincipal) securityContext.getUserPrincipal()).getUid();

            final int groupId = queryParams.getGroupId();

            final long cnt = historyService.getTotal(userId, groupId);

            logger.info("Retrieved total count {} for user {} in group {}", cnt, userId, groupId);
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
