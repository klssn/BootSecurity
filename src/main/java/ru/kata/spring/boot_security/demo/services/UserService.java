package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    void registerNewUser(User user, String role);

    void removeUserByEmail(String email);

    void editUser (User user, String role);

    User getUserById(Long id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}