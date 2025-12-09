package org.historyService.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.historyService.models.Group;

import java.util.List;

@ApplicationScoped
public class GroupRepository {
    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;

    @Transactional
    public void save(Group group) {
        entityManager.persist(group);
    }

    @Transactional
    public List<Group> getByUserId(int userId, int length, int start) {
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

    public boolean isGroupOwnedByUser(int groupId, int userId) {
        Group group = this.getById(groupId);
        return group != null && group.getOwnerId() == userId;
    }
}
