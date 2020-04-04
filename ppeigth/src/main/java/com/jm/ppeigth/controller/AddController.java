package com.jm.ppeigth.controller;

import com.jm.ppeigth.model.User;
import com.jm.ppeigth.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/registration")
public class AddController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute("id") User user,
                                @RequestParam("role") String role,
                                @RequestParam String password1) {
        ModelAndView modelAndView = new ModelAndView();
        if (passAndUname(user.getPassword(), user.getUsername(), password1, modelAndView)) {
            userService.addUsers(user, role);
        }
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

    private boolean passAndUname(String pass, String username, String pass1, ModelAndView modelAndView) {
        if (pass.equals("") || username.equals("") || !pass.equals(pass1)) {
            if (pass.equals("") || pass.length() < 5) {
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
