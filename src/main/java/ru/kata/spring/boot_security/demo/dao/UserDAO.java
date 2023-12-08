package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {

    void addNewUser(User user);

    void deleteUserByUsername(String username);

    void updateUserInfo(User user);

    User getUserByUsername(String username);
    User getUserById (Long id);
    List<User> getAllUsers();
}
