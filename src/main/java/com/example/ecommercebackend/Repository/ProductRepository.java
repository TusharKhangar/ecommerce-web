package com.example.ecommercebackend.Repository;

import com.example.ecommercebackend.Model.Category;
import com.example.ecommercebackend.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByCategory(Category category);
}
