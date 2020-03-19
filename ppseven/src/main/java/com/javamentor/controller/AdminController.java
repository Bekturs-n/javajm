package com.javamentor.controller;

import com.javamentor.model.Role;
import com.javamentor.model.User;
import com.javamentor.service.UserServiceImpl;
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
    public ModelAndView usersPageGet() {
        List<User> users = userService.getAllUser();
        Role role = new Role(1L, "ROLE_ADMIN");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("role", role);
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
        boolean role = user.getAuthorities().iterator().next().getAuthority().contains("ADMIN");
        modelAndView.setViewName("editPage");
        modelAndView.addObject("error1", error1);
        modelAndView.addObject("error2", error2);
        modelAndView.addObject("user_role", role);
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
        boolean bool = false;
        if (password == null) {
            bool = true;
        } else {
            user.setPassword(password);
        }
        if (passAndUsername(username, user, modelAndView)) {
            Set<Role> roles = new HashSet<>();
            user.setUsername(username);
            if (role.equals("ADMIN")) {
                user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
            } else {
                user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
            }
            userService.changeUserData(user.getId(), user, bool);
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

    private boolean passAndUsername(String username, User user, ModelAndView modelAndView) {
        if (username.equals("")) {
            modelAndView.addObject("error1", "Username cannot be empty!");
            return false;
        }
        if (userService.getUserByID(user.getId()).getUsername().equals(username)) {
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
