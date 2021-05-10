package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart WHERE user_id=:userId ORDER BY product_id", nativeQuery = true)
    List<Cart> getAllOrdersFromCart(@Param("userId") long userId);
}
