package com.example.ecommercebackend.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private int userId;

    private String name;
    private String email;

    private  String password;

    private  String address;

    private  String about;
    private  String gender;
    private  String phone;

    private Date date;

    private boolean active;

}
