package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Lazy
    @Autowired
    private UserDAO userDAO;

    @Lazy
    @Autowired
    private RoleDAO roleDAO;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {

        Role userRole = roleDAO.findByRole("USER");
        if (userRole == null) {
            userRole = new Role("USER");
            roleDAO.addRole(userRole);
        }
        Role adminRole = roleDAO.findByRole("ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ADMIN");
            roleDAO.addRole(adminRole);
        }

        if (userDAO.getUserByEmail("user@email.com") == null) {
            User user = new User("Name", "Surname", (byte) 30, "user@email.com", passwordEncoder.encode("password"));

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            userDAO.addNewUser(user);
        }

        if (userDAO.getUserByEmail("admin@email.com") == null) {
            User admin = new User("Name", "Surname", (byte) 30, "admin@email.com", passwordEncoder.encode("password"));

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(adminRole);
            admin.setRoles(roles);

            userDAO.addNewUser(admin);
        }
    }
}
