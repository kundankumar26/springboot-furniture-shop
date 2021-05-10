package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Cart;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.payload.response.CartResponse;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    public ResponseEntity<?> saveCart(Cart cart) {
        return new ResponseEntity<>(cartRepository.save(cart), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllOrdersFromCart(long userId) {
        List<Cart> cartList = cartRepository.getAllOrdersFromCart(userId);
        Set<Long> productIds = new HashSet<>();
        for(Cart cart: cartList){
            productIds.add(cart.getProductId());
        }
        List<Product> productList = productService.findProductsForCart(productIds);
        return new ResponseEntity<>(new CartResponse(cartList, productList), HttpStatus.OK);
    }

    public Cart addOrderToCart(long userId, long productId) {
        if(productService.findProductById(productId) == null){
            throw new RuntimeException("Cannot be created");
        }
        return cartRepository.save(new Cart(userId, productId));
    }

    public ResponseEntity<?> deleteFromCart(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cannot delete"));
        if(cart == null){
            return new ResponseEntity<>(new MessageResponse("Failed to delete"), HttpStatus.NOT_FOUND);
        }
        cartRepository.delete(cart);
        return new ResponseEntity<>(new MessageResponse("deleted successfully"), HttpStatus.NOT_FOUND);
    }
}
