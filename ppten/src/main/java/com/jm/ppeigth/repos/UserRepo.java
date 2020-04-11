package com.jm.ppeigth.repos;

import com.jm.ppeigth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends  JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAll();
}
