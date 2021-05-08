package com.example.furnitureshop.security.services;

import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.repository.ProductRepository;
import com.example.furnitureshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> findAllProducts() {
        return new ResponseEntity<>(productRepository.findAllProducts(), HttpStatus.OK);
    }

    public ResponseEntity<?> createProduct(int userId, Product product) {
        Object user = userRepository.findByUserId(userId);
        if(user==null){
            throw new RuntimeException("Cannot find user with id: " + userId);
        }
        product.setProductRating(0);
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }

    public Product findProductById(long productId) {
        Product product;
        try {
            product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("not found"));
        } catch(Exception e){
            return null;
        }
        return product;
    }

    public Product updateProduct(Product product){
        return productRepository.save(product);
    }
}
