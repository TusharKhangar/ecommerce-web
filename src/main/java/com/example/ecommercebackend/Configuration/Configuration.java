package com.example.ecommercebackend.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
}
