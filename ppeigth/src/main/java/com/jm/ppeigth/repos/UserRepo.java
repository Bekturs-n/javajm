package com.jm.ppeigth.repos;

import com.jm.ppeigth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long>, JpaRepository<User, Long> {
    User findByUsername(String username);
}
