package com.javamentor.controller;

import com.javamentor.model.Role;
import com.javamentor.model.User;
import com.javamentor.service.UserService;
import com.javamentor.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String userPage(Model model, Principal principal){
        String username = principal.getName();
        model.addAttribute("user", userService.getUserByName(username));
        return ("user");
    }
}
