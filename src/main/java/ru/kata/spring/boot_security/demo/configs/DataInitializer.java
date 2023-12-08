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
    public void run(String... args) throws Exception {
        if (userDAO.getUserByUsername("test") == null) {
            User user = new User("Name", "Surname", (byte) 30, "test", "email@example.com", passwordEncoder.encode("password"));

            Role userRole = roleDAO.findByRole("USER");
            if (userRole == null) {
                userRole = new Role("USER");
                roleDAO.addRole(userRole);

                Set<Role> roles = new HashSet<>();
                roles.add(userRole);
                user.setRoles(roles);
                userDAO.addNewUser(user);
            }
        }

        if (userDAO.getUserByUsername("admin") == null) {
            User admin = new User("Name", "Surname", (byte) 30, "admin", "admin_email@example.com", passwordEncoder.encode("password"));

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

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            roles.add(adminRole);
            admin.setRoles(roles);

            userDAO.addNewUser(admin);
        }
    }
}
