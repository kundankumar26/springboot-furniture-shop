package com.example.furnitureshop.security.services;

import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FurnituresRepository furnituresRepository;

    @Autowired
    private ProductRepository productRepository;

    //Get orders that are unchecked
    public ResponseEntity<?> getUncheckedOrders(){
        return new ResponseEntity<>(adminRepository.findAllOrdersForAdmin(), HttpStatus.OK);
    }

    //Get accepted or rejected orders
    public ResponseEntity<?> getOldOrders() {
        return new ResponseEntity<>(adminRepository.findOldOrders(), HttpStatus.OK);
    }

    public ResponseEntity<?> updateOrderByAdmin(Long orderId, Orders orderDetails) {

        Orders order = furnituresRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderId));
        order.setIsRejectedByAdmin(orderDetails.getIsRejectedByAdmin());
        order.setQty(orderDetails.getQty());
        Orders updatedOrder = furnituresRepository.save(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteOrder(Long orderId) {
        Orders order = furnituresRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order cannot be deleted"));
        furnituresRepository.delete(order);
        return new ResponseEntity<>(new MessageResponse("Order was deleted successfully."), HttpStatus.OK);
    }
}
