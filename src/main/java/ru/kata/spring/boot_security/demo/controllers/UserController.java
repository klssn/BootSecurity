package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getUser(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        return "user";
    }



    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }



    @GetMapping("/admin/addNewUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String addNewUserForm() {
        return "addNewUser";
    }

    @PostMapping("/admin/addNewUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String addNewUser(User user, @RequestParam String role) {
        userService.registerNewUser(user, role);
        return "redirect:/admin/users";
    }



    @PostMapping("/admin/deleteUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam String username) {
        userService.removeUserByUsername(username);
        return "redirect:/admin/users";
    }



    @GetMapping("/admin/editUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/editUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@ModelAttribute User user) {
        userService.editUser(user);
        return "redirect:/admin/users";
    }

}
