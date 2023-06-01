package com.example.ecommercebackend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    //it is int the set because products would be in a list


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Product> product;

//    private String title1;
    private String title;

    public Set<Product> getProduct(Set<Product> product) {
        return product;
    }

    public void setProduct(Set<Product> product) {
        this.product = product;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
