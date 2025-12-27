package org.authService.grpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GrpcChannelProvider {
    private static final Logger logger = LoggerFactory.getLogger(GrpcChannelProvider.class);

    private static final String HISTORY_SERVICE_HOST = System.getenv()
            .getOrDefault("HISTORY_SERVICE_HOST", "localhost");
    private static final int HISTORY_SERVICE_PORT = Integer.parseInt(System.getenv()
            .getOrDefault("HISTORY_SERVICE_PORT", "9090"));

    private ManagedChannel channel;

    @PostConstruct
    public void init() {
        logger.warn("{}:{}", HISTORY_SERVICE_HOST, HISTORY_SERVICE_PORT);
        logger.info("Initializing gRPC channel to {}:{}", HISTORY_SERVICE_HOST, HISTORY_SERVICE_PORT);
        channel = ManagedChannelBuilder
                .forAddress(HISTORY_SERVICE_HOST, HISTORY_SERVICE_PORT)
                .usePlaintext()
                .build();
    }

    public Channel getChannel() {
        if (channel == null) {
            throw new IllegalStateException("gRPC channel is not initialized");
        }
        return channel;
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.shutdown();
            logger.debug("gRPC channel shut down");
        }
    }
}
