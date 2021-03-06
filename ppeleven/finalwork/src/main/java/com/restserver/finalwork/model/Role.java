package com.restserver.finalwork.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userRoles;

    @ManyToMany(mappedBy = "roles")
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

//    @Override
//    public String getAuthority() {
//        return getUserRoles();
//    }

    @Override
    public String toString() {
        return userRoles;
    }
}
