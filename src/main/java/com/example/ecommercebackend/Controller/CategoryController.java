package com.example.ecommercebackend.Controller;

import com.example.ecommercebackend.Model.Category;
import com.example.ecommercebackend.payload.ApiResponse;
import com.example.ecommercebackend.payload.CategoryDto;
import com.example.ecommercebackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //create Category
    @PostMapping("/create")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    //update Category
    @PostMapping("/update/{categoryId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto update(@RequestBody CategoryDto categoryDto ,@PathVariable int categoryId) {
        return categoryService.update(categoryDto,categoryId);
    }

    //delete Category
    @DeleteMapping("/delete/{categoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable int categoryId) {
        this.categoryService.delete(categoryId);
    }

    //get Category By Id
    @GetMapping("/getCategory/{categoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CategoryDto getById(@PathVariable int categoryId ) {
      return this.categoryService.get(categoryId);
    }

    //get all Category by id
    @GetMapping("/getCategories")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CategoryDto> getAll() {
        return this.categoryService.getAll();
    }
}
