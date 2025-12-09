package org.historyService.controllers.rest;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.historyService.controllers.rest.DTO.ErrorResponse;
import org.historyService.controllers.rest.DTO.groups.CreateGroupResponse;
import org.historyService.controllers.rest.DTO.groups.CreateGroupsRequest;
import org.historyService.controllers.rest.DTO.groups.GetGroupsRequestQueryParams;
import org.historyService.controllers.rest.DTO.groups.GroupItemResponse;
import org.historyService.models.Group;
import org.historyService.services.GroupsService;
import org.jwtProcessing.filter.JwtSecured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtSecured
public class GroupsController {
    @Inject
    private GroupsService groupsService;

    private static final Logger logger = LoggerFactory.getLogger(GroupsService.class);

    @POST
    @Path("/create")
    public Response createGroup(
            @Context ContainerRequestContext requestContext,
            @Valid CreateGroupsRequest request
    ) {
        try {
            final DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");

            final int userId = token.getClaim("uid").asInt();

            final Group group = groupsService.createGroup(
                    userId, request.getGroupName()
            );

            logger.debug("Created new group {} for user {} with id {}", group.getGroupName(), userId, group.getId());
            return Response.ok(
                    new CreateGroupResponse(group.getId())
            ).build();

        } catch (Exception e) {
            logger.error("Error while creating group: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to create group"))
                    .build();
        }
    }

    @GET
    public Response get(
            @Context ContainerRequestContext requestContext,
            @Valid @BeanParam GetGroupsRequestQueryParams queryParams
    ) {
        try {
            final DecodedJWT token = (DecodedJWT) requestContext.getProperty("jwt");

            final int userId = token.getClaim("uid").asInt();

            final List<Group> groups = groupsService.getByUserId(
                    userId,
                    queryParams.getStart(),
                    queryParams.getLength()
            );

            final GroupItemResponse[] responses = groups.stream()
                    .map(g -> new GroupItemResponse(
                            g.getId(),
                            g.getGroupName()
                    ))
                    .toArray(GroupItemResponse[]::new);

            logger.debug("Retrieved {} groups for user {} in group", responses.length, userId);
            return Response.ok(responses).build();
        } catch (Exception e) {
            logger.error("Error retrieving groups count: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve groups count"))
                    .build();
        }
    }
}
