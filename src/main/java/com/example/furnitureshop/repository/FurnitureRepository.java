package com.example.furnitureshop.repository;


import com.example.furnitureshop.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FurnitureRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM orders WHERE emp_id=:empId ORDER BY order_date", nativeQuery = true)
    List<Orders> findUserByEmpId(@Param(value = "empId") long empId);

    @Query(value = "SELECT * FROM orders WHERE is_rejected_by_admin=1", nativeQuery = true)
    List<Orders> findOrdersForVendor();

    @Query(value = "SELECT * FROM orders WHERE emp_id=:empId and item_requested LIKE %:itemRequested%", nativeQuery = true)
    List<Orders> findIfItemExistForCurrentEmp(@Param(value = "empId") long empId, @Param(value = "itemRequested") String itemRequested);


}
