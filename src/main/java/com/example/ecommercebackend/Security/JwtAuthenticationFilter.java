package com.example.ecommercebackend.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
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
    Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        //getToken from Header
        String requestToken = request.getHeader("Authorization");
        logger.info("message {}" ,requestToken);

        String Username=null;
        String jwtToken=null;

        if(requestToken!=null && requestToken.trim().startsWith("Bearer")){
            // get actual token
            jwtToken=requestToken.substring(7);

            try {
                Username=this.jwtHelper.getUsername(jwtToken);

            }catch(ExpiredJwtException e) {
                logger.info("Invaild token message","Jwt token expire");
            }catch(MalformedJwtException e) {
                logger.info("Invaild token message","Invaild Jwt Token");
            }catch(IllegalArgumentException e) {
                logger.info("Invaild token message","unable to get token");
            }
            if(Username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                //validate
                UserDetails userDetails=this.userDetailsService.loadUserByUsername(Username);

                if (this.jwtHelper.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }else {
                    logger.info("not vaildate Message","invaild Jwt Token");

                }
            }else {
                logger.info("User Message","username is null or auth is allready there");
            }
        }else {
            logger.info("Token message {}","token does not start with bearer");
        }
        filterChain.doFilter(request,response);
    }
}
