package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE user_id=:userId ORDER BY product_id", nativeQuery = true)
    List<Cart> getAllOrdersFromCart(@Param("userId") long userId);

    @Query(value = "SELECT * FROM cart WHERE user_id=:userId and product_id=:productId", nativeQuery = true)
    Cart findByProductId(@Param("userId") long userId, @Param("productId") long productId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart WHERE user_id=:userId and product_id IN :productId", nativeQuery = true)
    void deleteByProductId(@Param(value = "userId") long userId, @Param(value = "productId") List<Long> productIds);
}
