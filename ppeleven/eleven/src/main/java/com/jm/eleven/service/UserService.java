package com.jm.eleven.service;

import com.jm.eleven.model.User;

import java.util.List;

public interface UserService {

    void addUsers(User user, String role);

    String getAllUser();

    User getUserByID(Long id);

    void changeUserData(User user, String role);

    void deleteUser(Long id);

//    boolean validateUser(User user);

    boolean nameIsEmpty(String login);

    User getUserByName(String name);
}
