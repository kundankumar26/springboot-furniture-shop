package com.example.furnitureshop.security.services;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.EmployeeResponseTable;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.repository.FurnituresRepository;
import com.example.furnitureshop.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private FurnituresRepository furnituresRepository;

    public ResponseEntity<?> getAllOrdersForVendor() {
        return new ResponseEntity<>(vendorRepository.findOrdersForVendor(), HttpStatus.OK);
    }

    public EmployeeResponseTable updateOrderByVendor(long orderId, EmployeeResponseTable orderDetails) {
        Orders order = furnituresRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderId));

        if(orderDetails.getShippedDate() != null) {
            if(orderDetails.getShippedDate().getTime() < order.getOrderDate().getTime()){
                throw new RuntimeException("Shipped date cannot be less than order date");
            }
            order.setShippedDate(orderDetails.getShippedDate());
            furnituresRepository.save(order);
            return orderDetails;
        }
        return null;
    }
}
