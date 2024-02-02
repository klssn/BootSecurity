package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserRESTController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            usersDTO.add(userToUserDTO(user));
        }
        return usersDTO;
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userToUserDTO(user);
        return userDTO;
    }

    @PostMapping("/users")
    public String addNewUser(@RequestBody UserDTO userDTO) {
        User user = userDTOToUser(userDTO);
        user.setPassword(userDTO.getPassword());
        userService.registerNewUser(user, userDTO.getRole());
        return "New user was successfully added";
    }

    @PutMapping("/users")
    public String editUser(@RequestBody UserDTO userDTO) {
        if (userService.getUserById(userDTO.getId()) != null) {
            User user = userDTOToUser(userDTO);
            user.setId(userDTO.getId());
            user.setPassword(userDTO.getPassword());
            userService.editUser(user, userDTO.getRole());
        }
        return "User was successfully edited";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return "User was successfully deleted";
    }

    public UserDTO userToUserDTO (User user) {
        UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail());
        Set<RoleDTO> rolesDTO = new LinkedHashSet<>();
        for (Role role : user.getRoles()) {
            rolesDTO.add(roleToRoleDTO(role));
        }
        userDTO.setRoles(rolesDTO);
        return userDTO;
    }

    public User userDTOToUser (UserDTO userDTO) {
        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getAge(), userDTO.getEmail(), userDTO.getPassword());
        return user;
    }


    public RoleDTO roleToRoleDTO (Role role) {
        return new RoleDTO(role.getRole());
    }



    /*
    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user;
    }

    @PostMapping("/users")
    public String addNewUser(@RequestBody User user, @RequestBody String role) {
        userService.registerNewUser(user, role);
        return "New user was successfully added";
    }

    @PutMapping("/users")
    public String editUser(@RequestBody User user, @RequestBody String role) {
        userService.editUser(user, role);
        return "User was successfully edited";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return "User was successfully deleted";
    }
    */

}
