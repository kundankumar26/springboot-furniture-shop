package com.example.furnitureshop.repository;


import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FurnitureRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM furniture WHERE emp_id=:empId ORDER BY order_date DESC", nativeQuery = true)
    List<Order> findUserByEmpId(@Param(value = "empId") long empId);

//    @Query(value = "SELECT emp_id FROM users WHERE username=:userName", nativeQuery = true)
//    Long findEmpIdByUsername(@Param(value = "userName") String username);

    @Query(value = "SELECT * FROM furniture WHERE is_rejected_by_admin=1", nativeQuery = true)
    List<Order> findOrdersForVendor();
}
