package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Cart;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.Wishlist;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.payload.response.WishlistResponse;
import com.example.furnitureshop.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductService productService;

    //Get products from wishlist
    public ResponseEntity<?> getProductsFromWishlist(long currentUserId) {
        List<Wishlist> wishlist = wishlistRepository.getProductsFromWishlist(currentUserId);
        Set<Long> productIds = new HashSet<>();
        for(Wishlist wishlistItem: wishlist){
            productIds.add(wishlistItem.getProductId());
        }
        List<Product> productList = productService.findProductsForCart(productIds);
        return new ResponseEntity<>(new WishlistResponse(wishlist, productList), HttpStatus.OK);
    }

    //Get items from wishlist for user
    public List<Wishlist> getWishlistForUser(long userId) {
        return wishlistRepository.getProductsFromWishlist(userId);
    }

    //Add product to wishlist
    public Object addProductToWishlist(long userId, long productId) {

        if(productService.findProductById(productId) == null){
            throw new RuntimeException("Cannot be created");
        }
        Wishlist wishlist = wishlistRepository.findByProductId(userId, productId);
        if(wishlist != null){
            wishlistRepository.delete(wishlist);
            return null;
        }
        return wishlistRepository.save(new Wishlist(userId, productId));
    }

    //Delete from wishlist
    public ResponseEntity<?> deleteFromWishlist(long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(() -> new RuntimeException("Cannot delete"));
        if(wishlist == null){
            return new ResponseEntity<>(new MessageResponse("Failed to delete"), HttpStatus.NOT_FOUND);
        }
        wishlistRepository.delete(wishlist);
        return new ResponseEntity<>(new MessageResponse("deleted successfully"), HttpStatus.NOT_FOUND);
    }
}
