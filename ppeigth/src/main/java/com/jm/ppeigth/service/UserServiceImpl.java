package com.jm.ppeigth.service;

import com.jm.ppeigth.model.Role;
import com.jm.ppeigth.model.User;
import com.jm.ppeigth.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }


    @Override
    public void addUsers(User user, String role) {
        user.setPassword(user.getPassword());
        user.setActive(true);
        if (role.equalsIgnoreCase("admin")) {
            user.setRoles(Collections.singleton(new Role(1l, "ROLE_ADMIN")));
        } else {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }

        userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User getUserByID(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        User user = optionalUser.isPresent() ? optionalUser.get() : new User();
        return user;
    }

    @Override
    public void changeUserData(Long id, User user, boolean pass) {
        User newUser = getUserByID(id);
        if (newUser != null) {
            if (pass) {
                newUser.setPassword(newUser.getPassword());
            } else {
                newUser.setPassword(user.getPassword());
            }
            newUser.setActive(true);
            newUser.setUsername(user.getUsername());
            newUser.setRoles(user.getRoles());
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public boolean validateUser(User user) {
        return getAllUser().stream()
                .anyMatch(f -> f.getUsername().equals(user.getUsername()) && f.getPassword().equals(user.getPassword()));
    }

    @Override
    public boolean nameIsEmpty(String username) {
        return getUserByName(username) == null;
    }

    @Override
    public User getUserByName(String username) {
        return userRepo.findByUsername(username);
    }
}
