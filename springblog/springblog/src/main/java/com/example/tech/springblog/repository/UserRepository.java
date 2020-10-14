package com.example.tech.springblog.repository;

import com.example.tech.springblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // we tell spring that this the repo used to handle the user

}
