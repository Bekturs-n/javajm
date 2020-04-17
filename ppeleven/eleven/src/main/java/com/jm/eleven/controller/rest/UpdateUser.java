package com.jm.eleven.controller.rest;

import com.jm.eleven.model.Role;
import com.jm.eleven.model.User;
import com.jm.eleven.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/update")
public class UpdateUser {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<String> update(@RequestParam String username,
                                         @RequestParam(name = "password", required = false) String password,
                                         @RequestParam("role") String role,
                                         @RequestParam("id") Long id) {
        User user = new User();
        if (!password.equals("")) {
            user.setPassword(password);
        }
        if (passAndUsername(username, id)) {
            user.setId(id);
            user.setUsername(username);
            userService.changeUserData(user, role);
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.OK);
    }

    private boolean passAndUsername(String username, Long id) {
        if (username.equals("")) {
            return false;
        }
        if (userService.getUserByID(id).getUsername().equals(username)) {
            return true;
        } else {
            if (!userService.nameIsEmpty(username)) {
                return false;
            }
        }
        return true;
    }
}
