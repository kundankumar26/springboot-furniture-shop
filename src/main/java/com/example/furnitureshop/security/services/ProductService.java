package com.example.furnitureshop.security.services;

import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Comment;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.repository.ProductRepository;
import com.example.furnitureshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    //Get all the products
    public ResponseEntity<?> findAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    //Create a product by vendor only
    public ResponseEntity<?> createProduct(int userId, Product product) {
        Object user = userRepository.findByUserId(userId);
        if(user==null){
            throw new RuntimeException("Cannot find vendor with id: " + userId);
        }
        product.setProductRating(0);
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }

    //Get a product by Id
    public Product findProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("not found"));
    }

    //Update product's qty after accepted by admin
    public Product updateProductQty(Product product){
        return productRepository.save(product);
    }

    //Get all products present in user's cart
    public List<Product> findProductsForCart(Set<Long> productIds) {
        List<Product> productList = null;
        try {
            productList = productRepository.findProductsForCart(productIds);
        } catch(Exception e){
            return null;
        }
        return productList;
    }

    //Update rating of a product
    public Product updateProductRating(Product product, double rating) {
        List<Comment> commentList = commentService.getCommentsByProduct(product.getProductId());
        double totalRating = commentList.size();
        double productRating = (product.getProductRating() * (totalRating-1) + rating) / totalRating;
        product.setProductRating(productRating);
        return productRepository.save(product);
    }
}
