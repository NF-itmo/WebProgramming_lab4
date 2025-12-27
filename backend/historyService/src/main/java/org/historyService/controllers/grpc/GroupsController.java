package org.historyService.controllers.grpc;

import groups.GroupsServiceGrpc;
import groups.Groups;
import io.grpc.stub.StreamObserver;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.historyService.services.GroupsService;

@Dependent
public class GroupsController extends GroupsServiceGrpc.GroupsServiceImplBase {
    @Inject
    GroupsService groupsService;

    @Override
    public void createGroup(Groups.CreateGroupRequest request, StreamObserver<Groups.CreateGroupResponse> responseObserver) {
        Groups.CreateGroupResponse.Builder responseBuilder = Groups.CreateGroupResponse.newBuilder();

        try {
            groupsService.createGroup(
                    request.getUserId(),
                    request.getGroupName()
            );
            responseBuilder.setIsOk(true)
                    .setMessage("Group created successfully");
        }  catch (Exception e) {
            responseBuilder.setIsOk(false)
                    .setMessage("Failed to create group: " + e.getMessage());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteGroup(Groups.DeleteGroupRequest request, StreamObserver<Groups.DeleteGroupResponse> responseObserver) {
        Groups.DeleteGroupResponse.Builder responseBuilder = Groups.DeleteGroupResponse.newBuilder();

        try {
            groupsService.deleteGroup(
                    request.getUserId(),
                    request.getGroupName()
            );
            responseBuilder.setIsOk(true)
                    .setMessage("Group deleted successfully");
        }  catch (Exception e) {
            responseBuilder.setIsOk(false)
                    .setMessage("Failed to delete group: " + e.getMessage());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
