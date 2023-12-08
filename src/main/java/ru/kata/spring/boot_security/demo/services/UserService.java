package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    void registerNewUser(User user, String role);

    void removeUserByUsername(String username);

    void editUser (User user);

    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
}
