package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addRole(Role role) {
        if (findByRole(role.getAuthority()) == null) {
            entityManager.persist(role);
        } else {
            entityManager.merge(role);
        }
    }

    @Override
    @Transactional
    public Role findByRole(String role) {
        try {
            return entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class)
                    .setParameter("role", role)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Exception in public Role findByRole(String roleName)");
            return null;
        }
    }

}