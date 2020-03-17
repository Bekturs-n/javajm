package com.javamentor.dao;

import com.javamentor.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    void addUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    void updateUser(Long id, User user);

    User findByName(String username);
}
