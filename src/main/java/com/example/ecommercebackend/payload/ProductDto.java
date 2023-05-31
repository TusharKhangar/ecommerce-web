package com.example.ecommercebackend.payload;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductDao {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int product_id;

        private String product_name;

        @Column(nullable = false)
        private double product_price;

        private boolean stock;

        private boolean live;

        private String product_imageName;

        private String product_description;

        private int product_quantity;
        @Override
        public String toString() {
            return "Product{" +
                    "product_id=" + product_id +
                    ", product_name='" + product_name + '\'' +
                    ", product_price=" + product_price +
                    ", stock=" + stock +
                    ", live=" + live +
                    ", product_imageName='" + product_imageName + '\'' +
                    ", product_description" +"='" + product_description + '\'' +
                    ", productQuantity ='" + product_quantity +
                    '}';
        }
    }

}
