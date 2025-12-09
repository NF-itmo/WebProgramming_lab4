package org.historyService.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.historyService.models.Point;

import java.util.List;

@ApplicationScoped
public class PointRepository {
    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;

    @Transactional
    public void save(Point point) {
        entityManager.persist(point);
    }

    @Transactional
    public List<Point> getByGroupId(int groupId, int length, int start) {
        return entityManager.createQuery("select p from Point p where p.group.id = :groupId order by p.timestamp desc", Point.class)
                .setParameter("groupId", groupId)
                .setFirstResult(start)
                .setMaxResults(length)
                .getResultList();
    }

    @Transactional
    public long getPointsCntByGroupId(int grouId) {
        return entityManager.createQuery("select count(p) from Point p where p.group.id = :grouId", Long.class)
                .setParameter("grouId", grouId)
                .getSingleResult();
    }
}

