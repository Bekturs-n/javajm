package com.javamentor.controller;

import com.javamentor.model.Role;
import com.javamentor.model.User;
import com.javamentor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView usersPageGet() {
        List<User> users = userService.getAllUser();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("userList", users);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView usersPagePost(@ModelAttribute("user") User user) {
        if (userService.validateUser(user)) {
            List<User> users = userService.getAllUser();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("admin");
            modelAndView.addObject("userList", users);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable long id,
                                 @RequestParam(name = "error1", required = false) String error1,
                                 @RequestParam(name = "error2", required = false) String error2) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserByID(id);
        modelAndView.setViewName("editPage");
        modelAndView.addObject("error1", error1);
        modelAndView.addObject("error2", error2);
        modelAndView.addObject("roles", Role.values());
        modelAndView.addObject("user_role", user.getRoles());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editPage(
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam("role") String role,
                           @ModelAttribute("id") User user) {

        ModelAndView modelAndView = new ModelAndView();
        if (passAndUsername(username, password, modelAndView)) {
            Set<Role> roles = new HashSet<>();
            user.setUsername(username);
            if (role.equals("ROLE_ADMIN")) {
                roles.add(Role.valueOf("ROLE_ADMIN"));
            }
            if (role.equals("ROLE_USER")) {
                roles.add(Role.valueOf("ROLE_USER"));
            }
            user.setRoles(roles);
            userService.changeUserData(user.getId(), user);
            modelAndView.setViewName("redirect:/user");
            return modelAndView;
        } else {
            modelAndView.setViewName("redirect:/admin/edit/" + user.getId());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePage(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        userService.deleteUser(id);
        return modelAndView;
    }

    private boolean passAndUsername(String pass, String username, ModelAndView modelAndView) {
        if (pass.equals("") || username.equals("")) {
            if (pass.equals("")) {
                modelAndView.addObject("error2", "Password cannot be empty!");
            }
            if (username.equals("")) {
                modelAndView.addObject("error1", "Username cannot be empty!");
            }
            return false;
        }
        if (!userService.nameIsEmpty(username)) {
            modelAndView.addObject("error1", "Please check another username");
            return false;
        }
        return true;
    }
}
