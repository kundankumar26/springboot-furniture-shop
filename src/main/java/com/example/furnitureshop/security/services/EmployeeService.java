package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.repository.EmployeeRepository;
import com.example.furnitureshop.repository.FurnituresRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FurnituresRepository furnituresRepository;

    //Get all the orders created by the current employee
    public ResponseEntity<?> findOrdersByUserId(long userId){
        return new ResponseEntity<>(employeeRepository.findAllOrdersForEmployee(userId), HttpStatus.OK);
    }

    //Create multiple orders
    public Orders createOrders(Orders order) {
        return furnituresRepository.save(order);
    }

    public boolean findIsProductOrdered(long userId, String productCategory) {
        List<Orders> orders = furnituresRepository.findIsProductOrdered(userId, productCategory);
        return orders.size() > 0;
    }
}
