package com.example.ecommercebackend.Configuration;

import com.example.ecommercebackend.Exception.ResourceNotFoundException;
import com.example.ecommercebackend.Model.User;
import com.example.ecommercebackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")) ;
        System.out.println(user.getEmail());
        return user;
    }
}
