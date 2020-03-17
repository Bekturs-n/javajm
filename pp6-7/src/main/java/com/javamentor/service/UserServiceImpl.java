package com.javamentor.service;

import com.javamentor.dao.UserDAO;
import com.javamentor.dao.UserDAOImpl;
import com.javamentor.model.Role;
import com.javamentor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static volatile UserServiceImpl instance;

    private UserDAO userDAO;
    private UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByName(username);
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            synchronized (UserServiceImpl.class) {
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void addUsers(String username, String password) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
//        user.setPassword(password);
        user.setUsername(username);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
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
    public void changeUserData(Long id, User user) {
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
        return userDAO.getAllUsers().stream()
                .filter(f -> f.getUsername().equals(name))
                .findFirst().orElse(null);
    }
}
