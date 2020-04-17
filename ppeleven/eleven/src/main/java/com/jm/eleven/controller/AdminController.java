package com.jm.eleven.controller;

import com.jm.eleven.model.Role;
import com.jm.eleven.model.User;
import com.jm.eleven.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashSet;
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
