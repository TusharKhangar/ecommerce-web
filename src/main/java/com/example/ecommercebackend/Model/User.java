package com.example.ecommercebackend.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class User implements UserDetails {
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

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Set<Roles> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                roles.stream().map((eachrole) ->
                        new SimpleGrantedAuthority(eachrole.getRoleName())).toList();
        simpleGrantedAuthorities.forEach(e -> {
            System.out.println(e.getAuthority());
        });
        return simpleGrantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled()  {
        return true;
    }
}
