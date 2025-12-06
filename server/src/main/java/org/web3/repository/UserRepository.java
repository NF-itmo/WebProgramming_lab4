package org.web3.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.web3.models.User;

@ApplicationScoped
public class UserRepository {
    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public User getByUsername(String username) {
        try{
            return entityManager.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
