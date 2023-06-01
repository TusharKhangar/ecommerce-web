package com.example.ecommercebackend.service;

import com.example.ecommercebackend.Exception.ResourceNotFoundException;
import com.example.ecommercebackend.Model.Category;
import com.example.ecommercebackend.Model.Product;
import com.example.ecommercebackend.Repository.CategoryRepository;
import com.example.ecommercebackend.Repository.ProductRepository;
import com.example.ecommercebackend.payload.CategoryDto;
import com.example.ecommercebackend.payload.ProductDto;
import com.example.ecommercebackend.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
   private  ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
   private CategoryRepository categoryRepository;

    public ProductDto createProduct(ProductDto productDto, int catId) {
//       fetch category is available or not
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("This category not found "));
        //productdto to product
        Product product = toEntity(productDto);
        product.setCategory(category);
        Product save =  productRepository.save(product);
//        Product save = productRepository.save(product);
        //product to productdto
        return toDto(save);
    }

    public ProductResponse viewAll(int pageNumber,int pageSize,String sortBy,String sortDir ) {
        Sort sort = null;
        if (sortDir.trim().equalsIgnoreCase("asc")) {
           sort =  Sort.by(sortBy).ascending();
            System.out.println(sort);
        }else {
           sort =   Sort.by(sortBy).descending();
            System.out.println(sort);
        }

       Pageable pageable =  PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product>  pageProduct=  page.getContent().stream().filter(Product::isLive).toList();
        List<ProductDto> collectDto = pageProduct.stream().map(this::toDto).toList();

        //creating response class
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(collectDto);
        productResponse.setPageNumber(page.getNumber());
        productResponse.setPageSize(page.getSize());
        productResponse.setTotalPage(page.getTotalPages());
        productResponse.setLastPage(page.isLast());

//        List<Product> findAll = productRepository.findAll();
//        return findAll.stream().map(this::toDto).collect(Collectors.toList());
        return productResponse;
    }

    public ProductDto viewProductById(int  productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(productId + " Product not found"));
        return this.toDto(product);
    }
    public void deleteProduct(int productId) {
        if (productRepository.existsById(productId))
            productRepository.deleteById(productId);
        else
            throw new ResourceNotFoundException("This product does not exists " + productId);
    }
    public ProductDto updateProduct(int productId, ProductDto newProduct) {
        Product oldProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("This product is not found"));
        oldProduct.setProduct_quantity(newProduct.getProduct_quantity());
        oldProduct.setProduct_description(newProduct.getProduct_description());
        oldProduct.setProduct_imageName(newProduct.getProduct_imageName());
        oldProduct.setProduct_name(newProduct.getProduct_name());
        oldProduct.setLive(newProduct.isLive());
        oldProduct.setStock(newProduct.isStock());
        oldProduct.setProduct_price(newProduct.getProduct_price());
        Product save = productRepository.save(oldProduct);
        return toDto(save);
    }


    //Product Dto to product
    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setProduct_name(productDto.getProduct_name());
        product.setProduct_price(productDto.getProduct_price());
        product.setProductId(productDto.getProductId());
        product.setProduct_description(productDto.getProduct_description());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProduct_quantity(productDto.getProduct_quantity());
        product.setProduct_imageName(productDto.getProduct_imageName());
        return product;
    }

    //product to ProductDto
    public ProductDto toDto(Product product) {
        ProductDto pd = new ProductDto();
        pd.setProductId(product.getProductId());
        pd.setProduct_name(product.getProduct_name());
        pd.setProduct_price(product.getProduct_price());
        pd.setLive(product.isLive());
        pd.setProduct_description(product.getProduct_description());
        pd.setStock(product.isStock());
        pd.setProduct_quantity(product.getProduct_quantity());
        pd.setProduct_imageName(product.getProduct_imageName());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategory_id(product.getCategory().getCategory_id());
        categoryDto.setTitle(product.getCategory().getTitle());

        //then set Category Dto in Product Dto
        pd.setCategory(categoryDto);
        return pd;

    }

    //We can also use Model Mapper and lombok library
    @GetMapping("/getCategory")
    public List<ProductDto> findProductByCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("This id category not found "));
        return productRepository.findByCategory(category).stream().map(this::toDto).toList();

    }
}
