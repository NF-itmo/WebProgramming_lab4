package org.historyService.controllers.rest;

import java.util.List;

import org.csrfSecurity.CsrfProvider;
import org.csrfSecurity.CsrfSecured;
import org.historyService.controllers.rest.DTO.ErrorResponse;
import org.historyService.controllers.rest.DTO.groups.CreateGroupResponse;
import org.historyService.controllers.rest.DTO.groups.CreateGroupsRequest;
import org.historyService.controllers.rest.DTO.groups.GetGroupsRequestQueryParams;
import org.historyService.controllers.rest.DTO.groups.GroupItemResponse;
import org.historyService.models.Group;
import org.historyService.services.GroupsService;
import org.jwtProcessing.filter.JwtSecured;
import org.jwtProcessing.security.JwtPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/groups")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@JwtSecured
@CsrfSecured
@CsrfProvider
public class GroupsController {
    @Inject
    private GroupsService groupsService;

    private static final Logger logger = LoggerFactory.getLogger(GroupsController.class);

    @POST
    @Path("/create")
    public Response createGroup(
            @Context SecurityContext securityContext,
            @Valid CreateGroupsRequest request
    ) {
        try {
            final int userId = ((JwtPrincipal) securityContext.getUserPrincipal()).getUid();

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
            @Context SecurityContext securityContext,
            @Valid @BeanParam GetGroupsRequestQueryParams queryParams
    ) {
        try {
            final JwtPrincipal jwtPrincipal = (JwtPrincipal) securityContext.getUserPrincipal();
            final int userId = jwtPrincipal.getUid();

            logger.info("Fetching groups for user ID: {}", userId);

            final List<Group> groups = groupsService.getByUserId(
                    userId,
                    queryParams.getStart(),
                    queryParams.getLength()
            );

            logger.info("Found {} groups for user {}", groups.size(), userId);
            
            final GroupItemResponse[] responses = groups.stream()
                    .map(g -> new GroupItemResponse(
                            g.getId(),
                            g.getGroupName()
                    ))
                    .toArray(GroupItemResponse[]::new);

            return Response.ok(responses).build();
        } catch (Exception e) {
            logger.error("Error retrieving groups: {}", e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to retrieve groups"))
                    .build();
        }
    }
}
