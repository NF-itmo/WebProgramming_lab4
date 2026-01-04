package org.historyService.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.historyService.controllers.rest.GroupsController;
import org.historyService.models.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class GroupRepository {
    private static final Logger logger = LoggerFactory.getLogger(GroupRepository.class);

    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;

    @Transactional
    public void save(Group group) {
        entityManager.persist(group);
    }

    @Transactional
    public List<Group> getByUserId(int userId, int start, int length) {
        return entityManager.createQuery("select g from Group g where g.ownerId = :userId order by g.timestamp desc", Group.class)
                .setParameter("userId", userId)
                .setFirstResult(start)
                .setMaxResults(length)
                .getResultList();
    }

    @Transactional
    public Group getById(int id) {
        return entityManager.find(Group.class, id);
    }

    @Transactional
    public void deleteByOwnerIdAndGroupName(int userId, String groupName) {
        entityManager.createQuery("delete from Group g where g.ownerId = :userId and g.groupName = :groupName")
                .setParameter("userId", userId)
                .setParameter("groupName", groupName)
                .executeUpdate();
    }

    @Transactional
    public boolean isGroupOwnedByUser(int userId, int groupId) {
        logger.info("Проверка владения: userId={}, groupId={}", userId, groupId);

        Group group = this.getById(groupId);

        if (group == null) {
            logger.info("Группа с id={} не найдена", groupId);
            return false;
        }

        boolean owns = Objects.equals(group.getOwnerId(), userId);
        logger.info("ownerId группы={}, userId={}, результат проверки={}", group.getOwnerId(), userId, owns);

        return owns;
    }

}
