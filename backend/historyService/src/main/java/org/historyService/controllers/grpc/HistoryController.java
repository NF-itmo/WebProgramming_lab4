package org.historyService.controllers.grpc;

import history.HistoryServiceGrpc;
import history.History.SavePointRequest;
import history.History.SavePointResponse;
import io.grpc.stub.StreamObserver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.historyService.services.HistoryService;
import org.historyService.services.exceptions.UnauthorizedException;

@ApplicationScoped
public class HistoryController extends HistoryServiceGrpc.HistoryServiceImplBase {
    @Inject
    private HistoryService historyService;

    @Override
    public void savePoint(SavePointRequest request, StreamObserver<SavePointResponse> responseObserver) {
        SavePointResponse.Builder responseBuilder = SavePointResponse.newBuilder();

        try {
            historyService.savePoint(
                    request.getX(),
                    request.getY(),
                    request.getR(),
                    request.getIsHitted(),
                    request.getUserId(),
                    request.getGroupId()
            );
            responseBuilder.setIsOk(true)
                    .setMessage("Point saved successfully");
        } catch (UnauthorizedException e) {
            responseBuilder.setIsOk(false)
                    .setMessage(e.getMessage());
        } catch (Exception e) {
            responseBuilder.setIsOk(false)
                    .setMessage("Failed to save point: " + e.getMessage());
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
