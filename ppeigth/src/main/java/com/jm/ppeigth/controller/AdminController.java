package com.jm.ppeigth.controller;

import com.jm.ppeigth.model.Role;
import com.jm.ppeigth.model.User;
import com.jm.ppeigth.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView usersPageGet(@RequestParam(name = "error1", required = false) String error1,
                                     @RequestParam(name = "error2", required = false) String error2,
                                     @RequestParam(name = "error3", required = false) String error3) {
        List<User> users = userService.getAllUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userList", users);
        modelAndView.addObject("error1", error1);
        modelAndView.addObject("error2", error2);
        modelAndView.addObject("error3", error3);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editPage(
            @RequestParam String username,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam("role") String role,
            @RequestParam("id") Long id) {

        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        boolean bool = false;
        if (password.equals("")) {
            bool = true;
        } else {
            user.setPassword(password);
        }
        if (passAndUsername(username, id, modelAndView)) {
            Set<Role> roles = new HashSet<>();
            user.setUsername(username);
            if (role.equalsIgnoreCase("admin")) {
                user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
            } else {
                user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            }
            userService.changeUserData(id, user, bool);
            modelAndView.setViewName("redirect:/admin");
            return modelAndView;
        } else {
            modelAndView.setViewName("redirect:/admin/edit/" + user.getId());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePage(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.deleteUser(id);
        return modelAndView;
    }

    private boolean passAndUsername(String username, Long id, ModelAndView modelAndView) {
        if (username.equals("")) {
            modelAndView.addObject("error1", "Username cannot be empty!");
            return false;
        }
        if (userService.getUserByID(id).getUsername().equals(username)) {
            return true;
        } else {
            if (!userService.nameIsEmpty(username)) {
                modelAndView.addObject("error1", "Please check another username");
                return false;
            }
        }
        return true;
    }
}
