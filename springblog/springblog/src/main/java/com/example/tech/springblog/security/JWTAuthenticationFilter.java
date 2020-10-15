package com.example.tech.springblog.security;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // this filter chain is managed by the servlet container

        // this method retrieves the json web token from the request
        String jwt = getJwtFronRequest(httpServletRequest);
    }

    private String getJwtFronRequest(HttpServletRequest httpServletRequest) {
        //retrive the authorization header from the request by getHeader method
        String bearerToken = httpServletRequest.getHeader("Authorization");

        //check if token has text and has bearer scheme
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            //retrieve all the text except the Bearer
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
