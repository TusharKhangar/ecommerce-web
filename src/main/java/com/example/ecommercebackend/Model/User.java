package com.example.ecommercebackend.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class User {
    //variables

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userId;

    @Column(nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private  String password;

    @Column(nullable = false)
    private  String address;

    private  String about;
    @Column(nullable = false)
    private  String gender;
    @Column(nullable = false,length = 10)
    private  String phone;

    @Column(name = "CreateAt")
    private Date date;

    private boolean active;

}
