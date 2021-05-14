package com.example.furnitureshop.security.services;

import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.*;
import com.example.furnitureshop.payload.response.ProductsForUserResponse;
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

    @Autowired
    private CartService cartService;

    @Autowired
    private WishlistService wishlistService;

    //Get all the products for everyone
    public ResponseEntity<?> findAllProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    //Get all the products for users with roles
    public ResponseEntity<?> findProductsForUser(long userId) {
        List<Cart> cartList = cartService.getOrdersForUser(userId);
        List<Wishlist> wishlists = wishlistService.getWishlistForUser(userId);
        return new ResponseEntity<>(new ProductsForUserResponse(productRepository.findALlProducts(), cartList, wishlists), HttpStatus.OK);
    }

    //Create a product by vendor only
    public ResponseEntity<?> createProduct(long userId, Product product) {
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
    public Product updateProductRating(long productId, double rating) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("not found"));
        if(product == null){
            throw new RuntimeException("Cannot create the comment");
        }
        double productRating = commentService.getRatingFromComments(product.getProductId());
        product.setProductRating(productRating);
        return productRepository.save(product);
    }
}
