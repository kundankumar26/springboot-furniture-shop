package com.example.furnitureshop.controllers;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/wishlist")
@PreAuthorize("hasRole('EMPLOYEE')")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getProductsFromWishlist() {
        ResponseEntity<?> responseEntity = null;
        try {
            long currentUserId = GlobalClassForFunctions.getUserIdFromToken();
            responseEntity = wishlistService.getProductsFromWishlist(currentUserId);
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Failed to get the orders from wishlist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable(value = "productId") String productid) {
        ResponseEntity<?> responseEntity = null;
        try {
            long userId = GlobalClassForFunctions.getUserIdFromToken();
            long productId = Long.parseLong(productid);
            responseEntity = new ResponseEntity<>(wishlistService.addProductToWishlist(userId, productId), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Failed to add to wishlist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{wishlistId}")
    public ResponseEntity<?> deleteFromWishlist(@PathVariable(value = "wishlistId") String wishlistid) {
        ResponseEntity<?> responseEntity = null;
        try {
            long wishlistId = Long.parseLong(wishlistid);
            responseEntity = wishlistService.deleteFromWishlist(wishlistId);
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Failed to delete from wishlist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("Deleted successfully."), HttpStatus.OK);
    }
}
