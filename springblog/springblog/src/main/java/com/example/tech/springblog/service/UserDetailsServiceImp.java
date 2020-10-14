package com.example.tech.springblog.service;

import com.example.tech.springblog.model.User;
import com.example.tech.springblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImp implements UserDetailsService {


    //loadUserByUserName - query database using username and return back the userDetails to the spring
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //wrap User into another user object
        User user = userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException
                ("User not found !!" + "-" + username));

        // creating Role_User authority to the method
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,true,true,
                true,getAuthorities("Role User"));

    }

    // create a simple grant authority object with the above role and return it back as a single ton list
    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList( new SimpleGrantedAuthority(role_user));
    }
    //wire this class to spring config class, so it will use for authentication
}
