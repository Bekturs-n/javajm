package com.jm.eleven.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public class Role implements GrantedAuthority {
    private Long id;

    private String userRoles;

    private transient Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String userRoles) {
        this.userRoles = userRoles;
    }

    public Role(Long id, String userRoles) {
        this.id = id;
        this.userRoles = userRoles;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String name) {
        this.userRoles = userRoles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getUserRoles();
    }

    @Override
    public String toString() {
        return userRoles;
    }
}
