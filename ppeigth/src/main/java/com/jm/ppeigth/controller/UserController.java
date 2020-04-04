package com.jm.ppeigth.controller;

import com.jm.ppeigth.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.GET)
    public String userPage(Model model, Principal principal){
        String username = principal.getName();
        model.addAttribute("user", userService.getUserByName(username));
        return ("user");
    }
}
