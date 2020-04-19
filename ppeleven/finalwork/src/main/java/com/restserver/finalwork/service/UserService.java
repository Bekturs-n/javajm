package com.restserver.finalwork.service;

import com.restserver.finalwork.model.User;

import java.util.List;

public interface UserService {

    void addUsers(User user);

    List<User> getAllUser();

    User getUserByID(Long id);

    void changeUserData(User user);

    void deleteUser(Long id);

    boolean validateUser(User user);

    boolean nameIsEmpty(String login);

    User getUserByName(String name);
}
