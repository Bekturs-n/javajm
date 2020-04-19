package com.jm.eleven.controller.rest;

import com.jm.eleven.model.Role;
import com.jm.eleven.model.User;
import com.jm.eleven.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/rest")    //rest
public class RestAdminController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(Long.toString(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password,
                                          @RequestParam("password1") String password1,
                                          @RequestParam("role") String role) {
        String errors = null;

        if ((errors = passAndUname(password, username, password1)).equals("")) {
            User user = new User(username, password);
            if (role.equalsIgnoreCase("admin")) {
                user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
            } else {
                user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            }
            userService.addUsers(user);
            return ResponseEntity.ok("");
        }
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestParam String username,
                                         @RequestParam(name = "password", required = false) String password,
                                         @RequestParam("role") String role,
                                         @RequestParam("id") Long id) {
        User user = new User();
        if (!password.equals("")) {
            user.setPassword(password);
        }

        if (role.equalsIgnoreCase("admin")) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
        } else {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }

        if (passAndUsername(username, id)) {
            user.setId(id);
            user.setUsername(username);
            userService.changeUserData(user);
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        return new ResponseEntity<>("error", HttpStatus.OK);
    }

    public boolean passAndUsername(String username, Long id) {
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

    public String passAndUname(String pass, String username, String pass1) {
        if (pass.equals("") || username.equals("") || !pass.equals(pass1)) {
            if (pass.equals("") || pass.length() < 5) {
                return "Error2";
            }
            if (!pass.equals(pass1)) {
                return "Error3";
            }
            if (username.equals("")) {
                return "Error1";
            }
        }
        return "";
    }

}
