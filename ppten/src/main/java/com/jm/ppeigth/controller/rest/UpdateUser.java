package com.jm.ppeigth.controller.rest;

import com.jm.ppeigth.model.Role;
import com.jm.ppeigth.model.User;
import com.jm.ppeigth.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String update(@RequestParam String username,
                         @RequestParam(name = "password", required = false) String password,
                         @RequestParam("role") String role,
                         @RequestParam("id") Long id) {
        User user = new User();
        boolean bool = false;
        if (password.equals("")) {
            bool = true;
        } else {
            user.setPassword(password);
        }
        if (passAndUsername(username, id)) {
            Set<Role> roles = new HashSet<>();
            user.setUsername(username);
            if (role.equalsIgnoreCase("admin")) {
                user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
            } else {
                user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            }
            userService.changeUserData(id, user, bool);
            return "";
        } else {
            return "error";
        }
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
