package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.AdminResponse;
import com.example.furnitureshop.models.EmployeeResponseTable;
import com.example.furnitureshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface AdminRepository extends JpaRepository<AdminResponse, Long> {

    @Query(value = "SELECT o.order_id, o.qty, o.is_rejected_by_admin, u.id, u.emp_id, u.emp_first_name, " +
            "u.emp_last_name, u.email, p.product_id, p.product_name, \n" +
            "p.product_price, p.product_qty FROM orders_table as o, products as p, users as u where " +
            "o.user_id=u.id and o.product_id=p.product_id", nativeQuery = true)
    List<AdminResponse> findAllOrdersForAdmin();

    @Query(value = "SELECT o.order_id, o.qty, o.is_rejected_by_admin, u.id, u.emp_id, u.emp_first_name, " +
            "u.emp_last_name, u.email, p.product_id, p.product_name, \n" +
            "p.product_price, p.product_qty FROM orders_table as o, products as p, users as u where " +
            "o.user_id=u.id and o.product_id=p.product_id and o.is_rejected_by_admin!=0", nativeQuery = true)
    List<AdminResponse> findOldOrders();

    @Query(value = "SELECT o.order_id, o.qty, o.is_rejected_by_admin, u.id, u.emp_id, u.emp_first_name, " +
            "u.emp_last_name, u.email, p.product_id, p.product_name, \n" +
            "p.product_price, p.product_qty FROM orders_table as o, products as p, users as u where " +
            "o.user_id=u.id and o.product_id=p.product_id and o.is_rejected_by_admin=0", nativeQuery = true)
    ArrayList<AdminResponse> findUncheckedOrders();
}
