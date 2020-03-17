package com.javamentor.service;

import com.javamentor.model.User;

import java.util.List;

public interface UserService {

    void addUsers(String username, String password);

    List<User> getAllUser();

    User getUserByID(Long id);

    void changeUserData(Long id, User user);

    void deleteUser(Long id);

    boolean validateUser(User user);

    User getUserByName(String name);

    boolean nameIsEmpty(String login);
}
