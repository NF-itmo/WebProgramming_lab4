package org.web3.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.web3.models.Point;

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
    public List<Point> getByUserId(int userId, int length, int start) {
        return entityManager.createQuery("select p from Point p where p.user.id = :userId order by p.timestamp desc", Point.class)
                .setParameter("userId", userId)
                .setFirstResult(start)
                .setMaxResults(length)
                .getResultList();
    }

    @Transactional
    public long getPointsCntByUserId(int userId) {
        return entityManager.createQuery("select count(p) from Point p where p.user.id = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}

