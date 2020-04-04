package com.javamentor.security;

import com.javamentor.security.handler.LoginSuccessHandler;
import com.javamentor.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.javamentor")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**", "/registration").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login")
                .and().logout().invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}