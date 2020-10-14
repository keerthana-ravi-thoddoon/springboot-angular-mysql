package com.example.tech.springblog.repository;

import com.example.tech.springblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    // we tell spring that this the repo used to handle the user

}
