package com.jm.ppeigth.service;

import com.jm.ppeigth.model.User;

import java.util.List;

public interface UserService {

    void addUsers(User user, String role);

    List<User> getAllUser();

    User getUserByID(Long id);

    void changeUserData(Long id, User user, boolean pass);

    void deleteUser(Long id);

    boolean validateUser(User user);

    boolean nameIsEmpty(String login);

    User getUserByName(String name);
}
