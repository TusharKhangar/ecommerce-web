package com.example.ecommercebackend;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EcommerceBackendApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(EcommerceBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //dishu for id -4
        System.out.println(passwordEncoder.encode("dishu"));
    }
}
