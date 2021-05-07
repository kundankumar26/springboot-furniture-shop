package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.EmployeeResponseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<EmployeeResponseTable, Long> {

    @Query(value = "SELECT o.order_id, o.qty, o.order_date, o.shipped_date, o.delivery_date, o.is_rejected_by_admin, " +
            "u.emp_first_name, u.emp_last_name, u.email, p.product_name, \n" +
            "p.product_category, p.product_price, p.product_rating, p.product_qty, p.product_image_url, a.address, a.phone_number\n" +
            "FROM orders_table as o, address as a, products as p, users as u where o.user_id=u.id and " +
            "o.address_id=a.address_id and o.product_id=p.product_id and is_rejected_by_admin=1", nativeQuery = true)
    List<EmployeeResponseTable> findOrdersForVendor();
}
