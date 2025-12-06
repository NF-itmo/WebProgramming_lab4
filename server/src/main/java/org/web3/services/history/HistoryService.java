package org.web3.services.history;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jws.HandlerChain;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import org.web3.models.Point;
import org.web3.repository.PointRepository;
import org.web3.services.history.DTOs.PointHistoryResponse;
import org.web3.services.history.DTOs.PointHistoryResponseArray;
import org.web3.services.history.DTOs.PointsCountResponse;

import java.util.List;

@WebService
@HandlerChain(file = "/handlers/BearerAuthHandler.xml")
public class HistoryService {
    @Resource private WebServiceContext webServiceContext;
    @Inject private PointRepository pointRepository;

    public PointHistoryResponseArray get(
            @WebParam(name = "start") final int start,
            @WebParam(name = "length") final int length
    ) {
        final int userId = getTokenFromContext().getClaim("uid").asInt();

        final List<Point> points = pointRepository.getByUserId(userId, length, start);

        return new PointHistoryResponseArray(
                points.stream()
                    .map(p -> new PointHistoryResponse(
                        p.getX(),
                        p.getY(),
                        p.getR(),
                        p.isHitted(),
                        p.getTimestamp()
                ))
                .toArray(PointHistoryResponse[]::new)
        );
    }

    public PointsCountResponse getTotal() {
        final int userId = getTokenFromContext().getClaim("uid").asInt();

        final long cnt = pointRepository.getPointsCntByUserId(userId);

        return new PointsCountResponse(cnt);
    }

    private DecodedJWT getTokenFromContext() {
        final Object tokenObj = webServiceContext.getMessageContext().get("token");
        if (!(tokenObj instanceof DecodedJWT token)) {
            throw new IllegalStateException("JWT token missing in context");
        }
        return token;
    }
}

