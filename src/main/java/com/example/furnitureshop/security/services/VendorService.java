package com.example.furnitureshop.security.services;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.repository.FurnituresRepository;
import com.example.furnitureshop.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private FurnituresRepository furnituresRepository;

    public ResponseEntity<?> getAllOrdersForVendor() {
        return new ResponseEntity<>(vendorRepository.findOrdersForVendor(), HttpStatus.OK);
    }

    public Orders updateOrderByVendor(long orderId, Orders orderDetails) {
        Orders order = furnituresRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderId));

//        System.out.println(order.getOrderDate()+" "+ orderDetails.getShippedDate() + " " +
//                order.getOrderDate().compareTo(orderDetails.getShippedDate()));

        if(orderDetails.getShippedDate() != null) {
            System.out.println(order.getOrderDate()+" "+ orderDetails.getShippedDate() + " " +
                    order.getOrderDate().compareTo(orderDetails.getShippedDate()));
            //System.out.println(new Date(new Date(System.currentTimeMillis()).getDate() - new Date(orderDetails.getShippedDate()));
            order.setShippedDate(orderDetails.getShippedDate());
            return furnituresRepository.save(order);
        }
        return null;
    }
}
