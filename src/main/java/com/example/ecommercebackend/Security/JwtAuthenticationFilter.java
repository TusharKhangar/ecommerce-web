package com.example.ecommercebackend.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    Logger logger =  LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper Jwthelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //getToken from Header
        String authorization = request.getHeader("Authorization");
        logger.info("message { }" , authorization);
        String userName = null;
        String JwtToken = null;
        if (authorization != null && authorization.trim().startsWith("Bearer")) {
            //get actual token
            JwtToken = authorization.substring(7);
            try {
                String username = this.Jwthelper.getUsername(JwtToken);
            } catch (ExpiredJwtException e) {
                logger.info("Invalid Token Message " , "Invalid Jwt Token");
            } catch (MalformedJwtException malformedJwtException) {
                logger.info("Invalid token message","Invalid Jwt Token ");
            } catch (IllegalArgumentException illegalArgumentException ) {
                logger.info("Invalid token message","unable to get token");
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //validate
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

                if (this.Jwthelper.validateToken(JwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    logger.info("not validate message" , "Invalid Jwt Token");
                }
            } else {
                    logger.info("user Message ","userName is null or auth is already there");
            }

        } else {
            logger.info("Token Message { }","token does not start with bearer");
        }
        filterChain.doFilter(request,response);
    }
}
