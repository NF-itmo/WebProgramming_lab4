package org.historyService.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import org.historyService.models.Group;
import org.historyService.models.Point;
import org.historyService.repository.GroupRepository;
import org.historyService.repository.PointRepository;
import org.historyService.services.exceptions.UnauthorizedException;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class HistoryService {
    private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);

    @Inject
    private PointRepository pointRepository;

    @Inject
    private GroupRepository groupRepository;

    public List<Point> get(
            final int userId,
            final int groupId,
            final int start,
            final int length
    ) throws UnauthorizedException {
        logger.debug("Getting points for user {} in group {} (start={}, length={})", userId, groupId, start, length);
        /*if (!groupRepository.isGroupOwnedByUser(userId, groupId)){
            throw new UnauthorizedException("You are not allowed to access this group");
        }*/
        return pointRepository.getByGroupId(groupId, start, length);
    }

    public long getTotal(
            final int userId,
            final int groupId
    ) throws UnauthorizedException {
        logger.debug("Getting total points for user {} in group {}", userId, groupId);
        /*if (!groupRepository.isGroupOwnedByUser(userId, groupId)){
            throw new UnauthorizedException("You are not allowed to access this group");
        }*/
        return pointRepository.getPointsCntByGroupId(groupId);
    }

    public void savePoint(
            final float X,
            final float Y,
            final float R,
            final boolean isHitted,
            final int userId,
            final int groupId
    ) throws UnauthorizedException {
        logger.debug("Saving point for user {} in group {}: X={}, Y={}, R={}, isHitted={}", userId, groupId, X, Y, R, isHitted);
        /*if (!groupRepository.isGroupOwnedByUser(userId, groupId)){
            throw new UnauthorizedException("You are not allowed to access this group");
        }*/
        final Group group = Group.builder().id(groupId).build();
        final Point point = Point.builder().x(X).y(Y).r(R).isHitted(isHitted).group(group).build();
        pointRepository.save(point);
    }
}