package com.javamentor.service;

import com.javamentor.dao.UserDAO;
import com.javamentor.model.Role;
import com.javamentor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByName(username);
    }


    @Override
    public void addUsers(User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        if (role.equals("ADMIN")) {
            user.setRoles(Collections.singleton(new Role(1l, "ROLE_ADMIN")));
        } else {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }

        userDAO.addUser(user);
    }

    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserByID(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void changeUserData(Long id, User user, boolean pass) {
        if (pass) {
            user.setPassword(userDAO.getUserById(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setActive(true);
        userDAO.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public boolean validateUser(User user) {
        return userDAO.getAllUsers().stream()
                .anyMatch(f -> f.getUsername().equals(user.getUsername()) && f.getPassword().equals(user.getPassword()));
    }

    @Override
    public boolean nameIsEmpty(String username) {
        return userDAO.getAllUsers().stream()
                .filter(f -> f.getUsername().equals(username))
                .count() == 0;
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.findByName(name);
    }
}
