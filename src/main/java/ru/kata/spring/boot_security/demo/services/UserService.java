package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    void registerNewUser(User user, String role);

    void removeUserByEmail(String email);

    void removeUserById(Long id);

    void editUser (User user, String role);

    User getUserById(Long id);
    User getUserByEmail(String email);
    List<User> getAllUsers();

    // REST
    UserDTO userToUserDTO (User user);
    User userDTOToUser (UserDTO userDTO);

}
