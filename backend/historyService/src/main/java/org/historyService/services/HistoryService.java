package org.historyService.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import org.historyService.models.Group;
import org.historyService.models.Point;
import org.historyService.repository.GroupRepository;
import org.historyService.repository.PointRepository;
import org.historyService.services.exceptions.UnauthorizedException;

import jakarta.inject.Inject;


@ApplicationScoped
public class HistoryService {
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
        if (!groupRepository.isGroupOwnedByUser(userId, groupId)){
            throw new UnauthorizedException("You are not allowed to access this group");
        }
        return pointRepository.getByGroupId(groupId, start, length);
    }

    public long getTotal(
            final int userId,
            final int groupId
    ) throws UnauthorizedException {
        if (!groupRepository.isGroupOwnedByUser(userId, groupId)){
            throw new UnauthorizedException("You are not allowed to access this group");
        }
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
        if (!groupRepository.isGroupOwnedByUser(userId, groupId)){
            throw new UnauthorizedException("You are not allowed to access this group");
        }
        final Group group = Group.builder().id(groupId).build();
        final Point point = Point.builder().x(X).y(Y).r(R).isHitted(isHitted).group(group).build();
        pointRepository.save(point);
    }
}