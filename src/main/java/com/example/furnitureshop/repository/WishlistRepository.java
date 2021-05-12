package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query(value = "SELECT * FROM wishlist WHERE user_id=:userId ORDER BY product_id", nativeQuery = true)
    List<Wishlist> getProductsFromWishlist(@Param(value = "userId") long userId);

    @Query(value = "SELECT * FROM wishlist WHERE user_id=:userId and product_id=:productId ORDER BY product_id LIMIT 1", nativeQuery = true)
    Wishlist findByProductId(@Param(value = "userId") long userId, @Param(value = "productId") long productId);
}
