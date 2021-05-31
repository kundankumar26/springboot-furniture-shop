package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FurnituresRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM orders_table WHERE user_id=:userId and product_category=:productCategory", nativeQuery = true)
    List<Orders> findIsProductOrdered(@Param(value = "userId") long userId,
                                      @Param(value = "productCategory") String productCategory);

    @Query(value = "SELECT * FROM orders_table", nativeQuery = true)
    List<Orders> getAllOrders();
}
