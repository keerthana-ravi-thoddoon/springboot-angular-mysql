package com.example.tech.springblog.controller;

import com.example.tech.springblog.dto.RegisterRequest;
import com.example.tech.springblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/signup")
    public void signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest); //passing register request object to the service


    }
}
