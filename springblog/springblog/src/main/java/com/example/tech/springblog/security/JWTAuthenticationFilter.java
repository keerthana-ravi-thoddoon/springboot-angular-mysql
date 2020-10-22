package com.example.tech.springblog.security;

import com.example.tech.springblog.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    @Qualifier("userDetailsServiceImp")
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        // this filter chain is managed by the servlet container

        // this method retrieves the json web token from the request
        String jwt = getJwtFronRequest(httpServletRequest);

        //check if retrived jwt has any text or not
        if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
          String username =  jwtTokenProvider.getUsernameFromJwt(jwt);

        //   Logic to retrieve the userdetails and set inside the security context
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
              userDetails.getAuthorities());
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }
        // if the above condition is satisfied then, filter is applied
        filterChain.doFilter(httpServletRequest,httpServletResponse);

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
