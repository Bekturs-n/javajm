package com.jm.eleven.service;

import com.google.gson.Gson;
import com.jm.eleven.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    private final String url = "http://localhost:8000/rest/user";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);
        return user;
    }


    @Override
    public void addUsers(User user) {
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        restTemplate.exchange(url + "/adduser",
                HttpMethod.POST,
                httpEntity,
                String.class);
    }

    @Override
    public String getAllUser() {
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url + "/getall", HttpMethod.GET, request, String.class);
        String account = response.getBody();
        return account;
    }

    @Override
    public User getUserByName(String username) {
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + "/getbyname/" + username,
                HttpMethod.GET,
                httpEntity,
                String.class);
        User user = new Gson().fromJson(responseEntity.getBody(), User.class);
        return user;
    }

    @Override
    public User getUserByID(Long id) {
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<User> responseEntity = restTemplate.exchange(url + "/getbyid/" + id,
                HttpMethod.GET,
                httpEntity,
                User.class);
        User user = responseEntity.getBody();
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(url + "/delete/" + id,
                HttpMethod.DELETE,
                httpEntity,
                String.class);
    }

    @Override
    public void changeUserData(User user) {
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        restTemplate.exchange(url + "/update",
                HttpMethod.PUT,
                httpEntity,
                String.class);
    }

    @Override
    public boolean nameIsEmpty(String username) {
        return getUserByName(username) == null;
    }
}
