package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    @Override
    public void registerNewUser(User user, String role) {
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleDAO.findByRole("ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ADMIN");
            roleDAO.addRole(adminRole);
        }
        Role userRole = roleDAO.findByRole("USER");
        if (userRole == null) {
            userRole = new Role("USER");
            roleDAO.addRole(userRole);
        }

        if ("ADMIN".equals(role)) {
            roles.add(userRole);
            roles.add(adminRole);
        } else if ("USER".equals(role)) {
            roles.add(userRole);
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        userDAO.addNewUser(user);
    }

    @Override
    public void removeUserByUsername(String username) {
        userDAO.deleteUserByUsername(username);
    }

    @Override
    public void editUser(User formUser) {
        User user = userDAO.getUserById(formUser.getId());
        if (user != null) {
            if (!formUser.getFirstName().equals(user.getFirstName())) {
                user.setFirstName(formUser.getFirstName());
            }
            if (!formUser.getLastName().equals(user.getLastName())) {
                user.setLastName(formUser.getLastName());
            }
            if (!formUser.getEmail().equals(user.getEmail())) {
                user.setEmail(formUser.getEmail());
            }
            if (formUser.getAge() != user.getAge()) {
                user.setAge(formUser.getAge());
            }
            if (formUser.getPassword() != null && !formUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(formUser.getPassword()));
            }

        }
        userDAO.updateUserInfo(user);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
