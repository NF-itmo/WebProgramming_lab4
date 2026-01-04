package org.geometryService.repository.history;

import history.HistoryServiceGrpc;
import history.History.SavePointRequest;
import history.History.SavePointResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HistoryServiceClient {
    private final HistoryServiceGrpc.HistoryServiceBlockingStub stub;
    private final ManagedChannel channel;

    public HistoryServiceClient() {
        this.channel = ManagedChannelBuilder
                .forAddress("backendHistoryService", 9090)
                .usePlaintext()
                .build();

        this.stub = HistoryServiceGrpc.newBlockingStub(channel);
    }

    public SavePointResponse savePoint(
            float x,
            float y,
            float r,
            boolean isHitted,
            int userId,
            int groupId
    ) {
        SavePointRequest request = SavePointRequest.newBuilder()
                .setX(x)
                .setY(y)
                .setR(r)
                .setIsHitted(isHitted)
                .setUserId(userId)
                .setGroupId(groupId)
                .build();

        return stub.savePoint(request);
    }

    public void shutdown() {
        channel.shutdown();
    }
}
