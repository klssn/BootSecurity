package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@EnableTransactionManagement
public class UserDAOImpl implements UserDAO {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addNewUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    @Transactional
    public void updateUserInfo(User user) {
        if (getUserById(user.getId()) != null) {
            entityManager.merge(user);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            String query = "FROM User WHERE username = :username";
            return entityManager.createQuery(query, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("No user found ");
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        List<User> users = query.getResultList();
        System.out.println("USER DAO " + users);
        return users;
    }
}
