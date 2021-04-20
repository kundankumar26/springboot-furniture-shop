package com.example.furnitureshop.repository;


import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FurnitureRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM furniture WHERE emp_id=:empId ORDER BY order_date DESC", nativeQuery = true)
    List<Order> findUserByEmpId(@Param(value = "empId") long empId);

    @Query(value = "SELECT emp_id FROM users WHERE username=:userName", nativeQuery = true)
    long findEmpIdByUsername(@Param(value = "userName") String username);
}
