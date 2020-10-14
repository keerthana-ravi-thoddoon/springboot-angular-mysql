package com.example.tech.springblog.service;

import com.example.tech.springblog.dto.LoginRequest;
import com.example.tech.springblog.dto.RegisterRequest;
import com.example.tech.springblog.model.User;
import com.example.tech.springblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void signup(RegisterRequest registerRequest) {
        User user = new User(); // creating a new user to map all the fields from our request to out user object
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword((registerRequest.getPassword())));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user); // save user object to the database
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    //entry point for login request
    public void login(LoginRequest loginRequest) {

    }
}
