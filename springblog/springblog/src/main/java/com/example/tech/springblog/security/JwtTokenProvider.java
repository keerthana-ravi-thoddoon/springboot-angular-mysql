package com.example.tech.springblog.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;

@Service
public class JwtTokenProvider {

    // create key object to reuse
    private Key key;

    @PostConstruct
    public void init(){
        //creates a key with HS512 algorithm
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // class responsible to create JSON token after authentication
    public String generateToken(Authentication authentication){
        //get principal and cast it to built in USer class of spring security
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key).compact();

    }

    public boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        return true;
    }
}
