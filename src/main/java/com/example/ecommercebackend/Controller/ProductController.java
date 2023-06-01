package com.example.ecommercebackend.Controller;


import com.example.ecommercebackend.Model.Product;
import com.example.ecommercebackend.payload.AppConstants;
import com.example.ecommercebackend.payload.ProductDto;
import com.example.ecommercebackend.payload.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.ecommercebackend.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create/{categories_id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductDto productDto , @PathVariable int categories_id) {
        return productService.createProduct(productDto,categories_id);
    }

    //this method is for view of all list of products added
    @GetMapping("/view")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductResponse viewAllProduct(
    @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER_STRING,required = false) int pageNumber ,
    @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE_STRING,required = false) int pageSize,
    @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY_STRING,required = false) String sortBy ,
    @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR_STRING,required = false) String sortDir){

        ProductResponse response = productService.viewAll(pageNumber,pageSize,sortBy,sortDir);
        return response;
    }


    //for view product by Id
    @GetMapping("/view/{productId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ProductDto viewProductById(@PathVariable int productId) {
        ProductDto product1 = productService.viewProductById(productId);
        return  product1;
    }

    //deleting product by id
    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return " Product Deleted ";
    }


    //updating the product
    @PutMapping("/update/{productId}")
    public ProductDto updateProduct(@PathVariable int productId ,@RequestBody ProductDto newProduct) {
        return productService.updateProduct(productId, newProduct);
    }

    //find product by category wise
    @GetMapping("/getCategory/{categoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProductDto> getProductByCategory(@PathVariable int categoryId) {
        return productService.findProductByCategory(categoryId);
    }
}
