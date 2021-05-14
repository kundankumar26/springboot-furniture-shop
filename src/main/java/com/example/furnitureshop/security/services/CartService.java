package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Cart;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.Wishlist;
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


    //Get all items from cart for user
    public ResponseEntity<?> getAllOrdersFromCart(long userId) {
        List<Cart> cartList = cartRepository.getAllOrdersFromCart(userId);
        Set<Long> productIds = new HashSet<>();
        for(Cart cart: cartList){
            productIds.add(cart.getProductId());
        }
        List<Product> productList = productService.findProductsForCart(productIds);
        return new ResponseEntity<>(new CartResponse(cartList, productList), HttpStatus.OK);
    }

    //Get all item name from cart for user
    public List<Cart> getOrdersForUser(long userId) {
        return cartRepository.getAllOrdersFromCart(userId);
    }

    //Add item to user's cart
    public Cart addOrderToCart(long userId, long productId) {
        if(productService.findProductById(productId) == null){
            throw new RuntimeException("Cannot be created");
        }
        Cart cart = cartRepository.findByProductId(userId, productId);
        if(cart != null){
            //cartRepository.delete(cart);
            return null;
        }
        return cartRepository.save(new Cart(userId, productId));
    }

    //Delete from user's cart
    public ResponseEntity<?> deleteFromCart(long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cannot delete"));
        if(cart == null){
            return new ResponseEntity<>(new MessageResponse("Failed to delete"), HttpStatus.NOT_FOUND);
        }
        cartRepository.delete(cart);
        return new ResponseEntity<>(new MessageResponse("deleted successfully"), HttpStatus.NOT_FOUND);
    }

    //Delete by product id
    public ResponseEntity<?> deleteByProductId(long userId, List<Long> productIds) {
        cartRepository.deleteByProductId(userId, productIds);
        return new ResponseEntity<>(new MessageResponse("deleted successfully"), HttpStatus.OK);
    }
}
