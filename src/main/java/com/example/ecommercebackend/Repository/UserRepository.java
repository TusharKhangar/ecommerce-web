package com.example.ecommercebackend.Repository;

import com.example.ecommercebackend.Model.User;
import com.example.ecommercebackend.payload.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface UserRepository extends JpaRepository<User, Integer> {

}
