package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE product_id IN :productIds ORDER BY product_id", nativeQuery = true)
    List<Product> findProductsForCart(@Param(value = "productIds") Set<Long> productIds);

    @Query(value = "SELECT * FROM products ORDER BY product_id", nativeQuery = true)
    List<Product> findALlProducts();
}
