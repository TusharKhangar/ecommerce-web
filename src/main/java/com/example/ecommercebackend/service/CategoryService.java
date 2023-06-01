package com.example.ecommercebackend.service;

import com.example.ecommercebackend.Exception.ResourceNotFoundException;
import com.example.ecommercebackend.Model.Category;
import com.example.ecommercebackend.Repository.CategoryRepository;
import com.example.ecommercebackend.payload.CategoryDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private  ModelMapper mapper;

    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        //category dto to Category
      Category category =  mapper.map(categoryDto, Category.class);
//        //categoryRepository save(Dto)
        Category saved  = categoryRepository.save(category);
//        //converting to dto and returning to the client
        return mapper.map(saved, CategoryDto.class);
    }

    public CategoryDto update(CategoryDto categoryDto,int categoryId) {
        //category dto to Category
        Category oldCategory = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found " + categoryId));
        oldCategory.setTitle(categoryDto.getTitle());
       var save =  categoryRepository.save(oldCategory);
        return this.mapper.map(save, CategoryDto.class);
    }

    public void delete(int categoryId) {
        Category oldCategory = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found " + categoryId));
        this.categoryRepository.delete(oldCategory);
    }

    public CategoryDto get(int categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found " + categoryId));

        return this.mapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> getAll()
    {
         List<Category> categoryList=  this.categoryRepository.findAll();
        return categoryList.stream().map(category -> this.mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
