package com.example.furnitureshop.repository;


import com.example.furnitureshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FurnitureRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM furniture WHERE emp_id=:empId ORDER BY order_date DESC", nativeQuery = true)
    List<Order> findUserByEmpId(@Param(value = "empId") long empId);

    @Query(value = "SELECT * FROM furniture WHERE is_rejected_by_admin=1", nativeQuery = true)
    List<Order> findOrdersForVendor();

    @Query(value = "SELECT * FROM furniture WHERE emp_id=:empId and item_requested LIKE %:itemRequested%", nativeQuery = true)
    List<Order> findIfItemExistForCurrentEmp(@Param(value = "empId") long empId, @Param(value = "itemRequested") String itemRequested);
}
