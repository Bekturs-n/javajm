package com.javamentor.controller;

import com.javamentor.model.User;
import com.javamentor.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView addPage(@RequestParam(name = "error1", required = false) String error1,
                                @RequestParam(name = "error2", required = false) String error2,
                                @RequestParam(name = "error3", required = false) String error3) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error1", error1);
        modelAndView.addObject("error2", error2);
        modelAndView.addObject("error3", error3);
        modelAndView.setViewName("addPage");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("id") User user,
                                @RequestParam("role") String role,
                                @RequestParam String password1,
                                @RequestParam String username,
                                @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        user.setPassword(password);
        user.setUsername(username);
        if (passAndUname(user.getPassword(), user.getUsername(), password1, modelAndView)) {
            userService.addUsers(user, role);
            modelAndView.setViewName("redirect:/admin");
            return modelAndView;
        } else {
            modelAndView.setViewName("redirect:/registration");
            return modelAndView;
        }
    }

    private boolean passAndUname(String pass, String username, String pass1, ModelAndView modelAndView) {
        if (pass.equals("") || username.equals("") || !pass.equals(pass1) || pass.length() < 4) {
            if (pass.equals("") || pass.length() < 4) {
                modelAndView.addObject("error2", "Please choose another password");
            }
            if (!pass.equals(pass1)) {
                modelAndView.addObject("error3", "Passwords do not match");
            }
            if (username.equals("")) {
                modelAndView.addObject("error1", "Username cannot be empty!");
            }
            return false;
        }
        if (!userService.nameIsEmpty(username) || username.length() < 4) {
            modelAndView.addObject("error1", "Please check another username");
            return false;
        }
        return true;
    }

}
