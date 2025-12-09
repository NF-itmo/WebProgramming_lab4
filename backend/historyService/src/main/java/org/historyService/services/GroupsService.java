package org.historyService.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.historyService.models.Group;
import org.historyService.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class GroupsService {
    @Inject
    private GroupRepository groupRepository;

    public Group createGroup(
            final int userId,
            final String groupName
    ) {
        final Group group = Group.builder()
                .ownerId(userId)
                .groupName(groupName)
                .build();
        groupRepository.save(group);
        return group;
    }

    public List<Group> getByUserId(
            final int userId,
            final int start,
            final int length
    ) {
        return groupRepository.getByUserId(
                userId,
                start,
                length
        );
    }
}
