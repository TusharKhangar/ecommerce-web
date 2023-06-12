package com.example.ecommercebackend.payload;

import com.example.ecommercebackend.Model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private UserDto userDto;
}
