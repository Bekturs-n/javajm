package com.jm.eleven.service;

import com.jm.eleven.model.User;

import java.util.List;

public interface UserService {

    void addUsers(User user);

    String getAllUser();

    User getUserByID(Long id);

    void changeUserData(User user);

    void deleteUser(Long id);

    boolean nameIsEmpty(String login);

    User getUserByName(String name);
}
