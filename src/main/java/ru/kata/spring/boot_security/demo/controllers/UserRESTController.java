package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRESTController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            usersDTO.add(userService.userToUserDTO(user));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersDTO);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        UserDTO userDTO = userService.userToUserDTO(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTO);
    }

    @GetMapping("/user/users/currentUser")
    public ResponseEntity<UserDTO> getUser(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        UserDTO userDTO = userService.userToUserDTO(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDTO);
    }

    @PostMapping("/users")
    public ResponseEntity<String> addNewUser(@RequestBody UserDTO userDTO) {
        User user = userService.userDTOToUser(userDTO);
        user.setPassword(userDTO.getPassword());
        userService.registerNewUser(user, userDTO.getRole());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/users")
    public ResponseEntity<String> editUser(@RequestBody UserDTO userDTO) {
        User userToEdit = userService.getUserById(userDTO.getId());
        if (userToEdit != null) {
            User user = userService.userDTOToUser(userDTO);
            user.setId(userDTO.getId());
            user.setPassword(userDTO.getPassword());
            userService.editUser(user, userDTO.getRole());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.removeUserById(id);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
