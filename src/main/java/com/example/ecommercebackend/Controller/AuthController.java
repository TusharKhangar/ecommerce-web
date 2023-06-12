package com.example.ecommercebackend.Controller;

import com.example.ecommercebackend.Exception.ResourceNotFoundException;
import com.example.ecommercebackend.Security.JwtHelper;
import com.example.ecommercebackend.payload.JwtRequest;
import com.example.ecommercebackend.payload.JwtResponse;
import com.example.ecommercebackend.payload.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public JwtResponse login(@RequestBody JwtRequest request) {
       this.authenticateUser(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setUserDto(this.mapper.map(userDetails, UserDto.class));
        return jwtResponse;
    }

    private void authenticateUser(String userName, String password) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (BadCredentialsException e) {
            throw new ResourceNotFoundException("Invalid UserName or Password");
        }
        catch (DisabledException e) {
            throw new ResourceNotFoundException("Username is not active");
        }
    }
}
