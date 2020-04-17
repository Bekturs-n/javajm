package com.jm.eleven.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @RequestMapping(value = "/")
    public String home() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login")
    public String getIndex(@RequestParam(name = "error", required = false) Boolean error,
                           Model model, Authentication authentication) {
        if (authentication != null) {
            if (authentication.getAuthorities().iterator().next().getAuthority().contains("ADMIN")) {
                return "redirect:/admin";
            } else {
                return "redirect:/user";
            }
        }
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", "Неверные данные!");
        }
        return "login";
    }
}
