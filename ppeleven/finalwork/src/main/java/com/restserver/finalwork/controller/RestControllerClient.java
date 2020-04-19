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
@RequestMapping(value = "/rest")
public class RestControllerClient {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/user/getall", method = RequestMethod.GET)
    public String getAllUsers() {
        String json = new Gson().toJson(userService.getAllUser());
        return json;
    }

    @RequestMapping(value = "/user/getbyname/{username}", method = RequestMethod.GET)
    public String getUser(@PathVariable String username) {
        User user = userService.getUserByName(username);
        String json = new Gson().toJson(user);
        return json;
    }

    @RequestMapping(value = "/user/getbyid/{id}", method = RequestMethod.GET) //userbyid
    public String getUser(@PathVariable long id) {
        User user = userService.getUserByID(id);
        String json = new Gson().toJson(user);
        return json;
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping(value = "/user/adduser", consumes = "application/json")
    public void addUser(@RequestBody User user) {
        userService.addUsers(user);
    }

    @PutMapping(value = "/user/update", consumes = "application/json")
    public void updateUser(@RequestBody User user) {
        userService.changeUserData(user);
    }
}
