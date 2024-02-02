package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDAO {

    void addNewUser(User user);

    void deleteUserByEmail(String email);

    void deleteUserById(Long id);

    void updateUserInfo(User user);

    User getUserByEmail(String email);
    User getUserById (Long id);
    List<User> getAllUsers();
}
