package com.restserver.finalwork.controller;

import com.google.gson.Gson;
import com.restserver.finalwork.model.Role;
import com.restserver.finalwork.model.User;
import com.restserver.finalwork.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.awt.*;
import java.util.Collections;

@RestController
@RequestMapping(value = "/server")
public class RestControllerClient {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public String getAllUsers() {
        String json = new Gson().toJson(userService.getAllUser());
        return json;
    }

    @RequestMapping(value = "/auth/{username}", method = RequestMethod.GET)
    public String getUser(@PathVariable String username) {
        User user = userService.getUserByName(username);
        String json = new Gson().toJson(user);
        return json;
    }

    @RequestMapping(value = "/byid/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable long id) {
        User user = userService.getUserByID(id);
        String json = new Gson().toJson(user);
        return json;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping(value = "/{role}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@RequestBody User user,
                        @PathVariable String role) {
        userService.addUsers(user, role);
    }

    @PutMapping(value = "/{role}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody User user,
                           @PathVariable String role) {
        if (role.equalsIgnoreCase("admin")) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
        } else {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        }
        userService.changeUserData(user);
    }
}
