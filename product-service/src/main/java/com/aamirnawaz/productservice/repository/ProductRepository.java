package com.aamirnawaz.productservice.repository;

import com.aamirnawaz.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
