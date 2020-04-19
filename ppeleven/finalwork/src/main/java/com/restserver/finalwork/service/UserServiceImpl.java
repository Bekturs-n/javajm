package com.restserver.finalwork.service;

import com.restserver.finalwork.model.Role;
import com.restserver.finalwork.model.User;
import com.restserver.finalwork.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addUsers(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByID(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        User user = optionalUser.isPresent() ? optionalUser.get() : new User();
        return user;
    }

    @Override
    public void changeUserData(User user) {
        User newData = getUserByID(user.getId());
        if (newData != null) {
            if(user.getPassword() != null){
                newData.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            newData.setActive(true);
            newData.setUsername(user.getUsername());
            newData.setRoles(user.getRoles());
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
