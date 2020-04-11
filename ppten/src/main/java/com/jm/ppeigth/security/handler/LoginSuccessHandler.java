package com.jm.ppeigth.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        String targetUrl;
        if (authentication.getAuthorities().iterator().next().getAuthority().contains("ADMIN")) {
            targetUrl = "/admin";
        } else {
            targetUrl = "/user";
        }
        httpServletResponse.sendRedirect(targetUrl);

    }
}