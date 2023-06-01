package com.example.ecommercebackend.payload;

import com.example.ecommercebackend.Model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private List<ProductDto> content;
    private int pageNumber;
    private int pageSize;
    private int totalPage;
    private boolean lastPage;
}
