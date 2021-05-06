package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM orders_table_new as o, address as a, products as p, users as u where o.user_id==u.id and o.address_id==a.address_id and o.product_id==p.product_id", nativeQuery = true)
    Object findAllOrdersByEmployee();

}
