package com.example.ecommercebackend.payload;

import com.example.ecommercebackend.Model.Category;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto  {
        private int productId;
        private String product_name;
        private double product_price;
        private boolean stock;
        private boolean live;
        private String product_imageName;
        private String product_description;
        private int product_quantity;

        private CategoryDto category;

}
