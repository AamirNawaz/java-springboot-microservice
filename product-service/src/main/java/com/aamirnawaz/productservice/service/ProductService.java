package com.aamirnawaz.productservice.service;

import com.aamirnawaz.productservice.dto.ProductRequest;
import com.aamirnawaz.productservice.dto.ProductResponse;
import com.aamirnawaz.productservice.model.Product;
import com.aamirnawaz.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        System.out.println(productRequest);
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product is saved with ID:{}", product.getId());
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );

    }

    public ResponseEntity<ProductResponse> createNewProduct(ProductRequest productRequest) {
        System.out.println(productRequest);
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product is saved with ID:{}", product.getId());
        return ResponseEntity.status(200).body(new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        ));

    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public ResponseEntity<Optional<Product>> getProduct(String id) {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findById(id));
    }

    public ResponseEntity<ProductResponse> updateProduct(String id, ProductRequest productRequest) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product Not found with Given" + id));
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());

            Product updateProduct = productRepository.save(product);

            ProductResponse productResponse = convertToProductResponse(updateProduct);
            return ResponseEntity.ok().body(productResponse);

        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }


    public ResponseEntity<String> deleteProduct(String id) {
        System.out.println(id);
        productRepository.deleteById(id);
        log.info("Product with ID:{} Deleted successfully!", id);
        return ResponseEntity.ok().body("Product with ID:" + id + " Deleted successfully!");
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .Id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    private ProductResponse convertToProductResponse(Product updateProduct) {
        return new ProductResponse(
                updateProduct.getId(),
                updateProduct.getName(),
                updateProduct.getDescription(),
                updateProduct.getPrice());
    }


}

