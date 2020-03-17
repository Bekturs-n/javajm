package com.javamentor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
    @RequestMapping(value = "/")
    public String home() {
        return "redirect:/user";
    }

    @RequestMapping(value = "/login")
    public String getIndex(@RequestParam(name = "error", required = false) Boolean error,
                           Model model){
        if (Boolean.TRUE.equals(error)){
            model.addAttribute("error", "Bad credentails!");
        }
        return "login";
    }
}
