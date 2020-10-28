package com.example.tech.springblog.controller;

import com.example.tech.springblog.dto.LoginRequest;
import com.example.tech.springblog.dto.RegisterRequest;
import com.example.tech.springblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService; // will throw an error if we do not give @Service in AuthService class

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        System.out.println("in auth controller");
        authService.signup(registerRequest); //passing register request object to the service
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){

       return authService.login(loginRequest);

    }
}
