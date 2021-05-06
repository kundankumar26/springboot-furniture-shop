package com.example.furnitureshop.controllers;

import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping(value = "/")
    public ResponseEntity<?> findAllProducts() {
        ResponseEntity<?> responseEntity;
        try {
            responseEntity = productService.findAllProducts();
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot get the products"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/{userIdString}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> createProduct(@PathVariable String userIdString, @RequestBody Product product) {
        ResponseEntity<?> responseEntity;
        try {
            int userId = Integer.parseInt(userIdString);
            responseEntity = productService.createProduct(userId, product);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot create the product by userid: " + userIdString), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }
}
