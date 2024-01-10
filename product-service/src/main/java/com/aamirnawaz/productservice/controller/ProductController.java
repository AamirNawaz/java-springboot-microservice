package com.aamirnawaz.productservice.controller;

import com.aamirnawaz.productservice.dto.ProductRequest;
import com.aamirnawaz.productservice.dto.ProductResponse;
import com.aamirnawaz.productservice.model.Product;
import com.aamirnawaz.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //Create Product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    //Create Product
    //With ResponseEntity
    @PostMapping("new-product")
    public ResponseEntity<ProductResponse> createNewProduct(@RequestBody ProductRequest productRequest) {
        return productService.createNewProduct(productRequest);

    }

    //Get Products
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    //Get Product by Id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    //update product by Id
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    //Delete Product by Id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }

}
