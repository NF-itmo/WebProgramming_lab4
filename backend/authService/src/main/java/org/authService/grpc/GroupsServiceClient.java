package org.authService.grpc;

import groups.GroupsServiceGrpc;
import groups.Groups;
import io.grpc.StatusRuntimeException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class GroupsServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(GroupsServiceClient.class);

    @Inject
    private GrpcChannelProvider channelProvider;

    public boolean createDefaultGroup(int userId, String groupName) {
        try {
            GroupsServiceGrpc.GroupsServiceBlockingStub stub = 
                    GroupsServiceGrpc.newBlockingStub(channelProvider.getChannel());
            
            Groups.CreateGroupRequest request = Groups.CreateGroupRequest.newBuilder()
                    .setUserId(userId)
                    .setGroupName(groupName)
                    .build();
            
            Groups.CreateGroupResponse response = stub.createGroup(request);
            
            if (!response.getIsOk()) {
                logger.warn("Failed to create default group for user {}: {}", userId, response.getMessage());
                return false;
            }
            
            logger.info("Default group '{}' created for user {}", groupName, userId);
            return true;
            
        } catch (StatusRuntimeException e) {
            logger.error("gRPC error while creating default group for user {}: {} - {}", 
                    userId, e.getStatus().getCode(), e.getStatus().getDescription());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error while creating default group for user {}", userId, e);
            return false;
        }
    }

    public boolean deleteGroup(int userId, String groupName) {
        try {
            GroupsServiceGrpc.GroupsServiceBlockingStub stub = 
                    GroupsServiceGrpc.newBlockingStub(channelProvider.getChannel());
            
            Groups.DeleteGroupRequest request = Groups.DeleteGroupRequest.newBuilder()
                    .setUserId(userId)
                    .setGroupName(groupName)
                    .build();
            
            Groups.DeleteGroupResponse response = stub.deleteGroup(request);
            
            if (!response.getIsOk()) {
                logger.warn("Failed to delete group for user {}: {}", userId, response.getMessage());
                return false;
            }
            
            logger.info("Group '{}' deleted for user {} (compensation)", groupName, userId);
            return true;
            
        } catch (StatusRuntimeException e) {
            logger.error("gRPC error while deleting group for user {}: {} - {}", 
                    userId, e.getStatus().getCode(), e.getStatus().getDescription());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error while deleting group for user {}", userId, e);
            return false;
        }
    }
}
