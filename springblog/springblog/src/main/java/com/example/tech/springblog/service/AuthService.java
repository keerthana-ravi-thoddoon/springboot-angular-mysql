package com.example.tech.springblog.service;

import com.example.tech.springblog.dto.LoginRequest;
import com.example.tech.springblog.dto.RegisterRequest;
import com.example.tech.springblog.model.User;
import com.example.tech.springblog.repository.UserRepository;
import com.example.tech.springblog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    // login to perform authentication
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
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
    public String login(LoginRequest loginRequest) {
        // once login request passes through this method, user is authenticated
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        //storing the return type of authenticate method in spring security's security context
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //implementation of authentication process
       return jwtTokenProvider.generateToken(authenticate);
    }

    public Optional <org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
