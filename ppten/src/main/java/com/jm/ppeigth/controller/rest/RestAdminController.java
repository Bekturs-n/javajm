package com.jm.ppeigth.controller.rest;

import com.jm.ppeigth.model.User;
import com.jm.ppeigth.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajax")
public class RestAdminController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(Long.toString(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestParam("username") String username, // @RequestBody
                          @RequestParam("password") String password,
                          @RequestParam("password1") String password1,
                          @RequestParam("role") String role) {
        String errors = null;
        if ((errors = passAndUname(password, username, password1)).equals("")) {
            User user = new User(username, password);
            userService.addUsers(user, role);
            return ResponseEntity.ok("");
        }
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }


    private String passAndUname(String pass, String username, String pass1) {
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
        if (!userService.nameIsEmpty(username) || username.length() < 4) {
            return "Error1";
        }
        return "";
    }
}
