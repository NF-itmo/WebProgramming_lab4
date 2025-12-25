package org.historyService.controllers.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class GrpcServerProvider {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServerProvider.class);

    private Server server;

    private final HistoryController historyController = new HistoryController();

    @PostConstruct
    public void init() {
        try {
            server = ServerBuilder
                    .forPort(9090)
                    .addService(historyController)
                    .build()
                    .start();

            logger.debug("gRPC server started on port 9090");
        } catch (Exception e) {
            throw new RuntimeException("Failed to start gRPC server", e);
        }
    }

    @PreDestroy
    public void shutdown() {
        if (server != null) {
            server.shutdown();
        }
    }
}
