package com.example.furnitureshop.controllers;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.models.Cart;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('EMPLOYEE')")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getOrdersCart() {
        ResponseEntity<?> responseEntity = null;
        try {
            long currentUserId = GlobalClassForFunctions.getUserIdFromToken();
            responseEntity = cartService.getAllOrdersFromCart(currentUserId);
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Failed to get the orders from cart"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> addOrder(@RequestBody Product product) {
        ResponseEntity<?> responseEntity = null;
        try {
            long userId = GlobalClassForFunctions.getUserIdFromToken();
            responseEntity = new ResponseEntity<>(cartService.addOrderToCart(userId, product.getProductId()), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Failed to add to cart"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{cartId}")
    public ResponseEntity<?> deleteFromCart(@PathVariable(value = "cartId") String cartid) {
        ResponseEntity<?> responseEntity = null;
        try {
            long cartId = Long.parseLong(cartid);
            responseEntity = cartService.deleteFromCart(cartId);
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Failed to delete"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("Deleted successfully."), HttpStatus.OK);
    }
}
