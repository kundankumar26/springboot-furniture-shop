package com.example.furnitureshop.security.services;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.repository.FurnitureRepository;
import com.example.furnitureshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Service
public class FurnitureService {

    @Autowired
    private FurnitureRepository furnitureRepository;

    @Autowired
    private UserRepository userRepository;

    //GET ALL THE ORDERS
    public ResponseEntity<?> getAllOrders(){
        return new ResponseEntity<>(furnitureRepository.findAll(), HttpStatus.OK);
    }

    //GET ALL ORDERS FOR VENDOR
    public ResponseEntity<?> getAllOrdersForVendor() {
        return new ResponseEntity<>(furnitureRepository.findOrdersForVendor(), HttpStatus.OK);
    }

    //GET ORDER BY EMP ID
    public ResponseEntity<?> getOrderByEmpId(long empId){
        return new ResponseEntity<>(furnitureRepository.findUserByEmpId(empId), HttpStatus.OK);
    }

    //GET SINGLE ORDER
    public ResponseEntity<?> getSingleOrder(Long orderId) {
        Order order = furnitureRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no ");
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //CREATE AN ORDER
    public ResponseEntity<?> createOrder(List<Order> orderDetails) {
        List<Order> createdOrders = new ArrayList<>();
        for(Order order: orderDetails){
            createdOrders.add(furnitureRepository.save(order));
        }
        return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);
    }

    //UPDATE ORDER BY ADMIN
    public ResponseEntity<?> updateOrder(Long id, Order orderDetails) {
        Order order = furnitureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + id));
        order.setIsRejectedByAdmin(orderDetails.getIsRejectedByAdmin());
        order.setQty(orderDetails.getQty());
        Order updatedOrder = furnitureRepository.save(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.ACCEPTED);
    }

    //UPDATE ORDER BY VENDOR
    public Order updateOrderByVendor(long orderId, Order orderDetails){
        Order order = furnitureRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + orderId));
        order.setShippedDate(GlobalClassForFunctions.getCurrentDateAndTime(orderDetails.getShippedDate()));
        Order updatedOrder = furnitureRepository.save(order);
        return updatedOrder;
    }

    //DELETE ORDER
    public ResponseEntity<?> deleteOrder(Long orderId) {
        Order order = furnitureRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order cannot be deleted"));
        furnitureRepository.delete(order);
        return new ResponseEntity<>(new MessageResponse("Order was deleted successfully."), HttpStatus.ACCEPTED);
    }

    //RETURN EMPLOYEE ID FOR USERNAME
    public ResponseEntity<?> findEmpIdByUsername(String username){
        return new ResponseEntity<>(userRepository.findEmpIdByUsername(username), HttpStatus.OK);
    }

    //RETURN USER OBJECT FROM EMPLOYEE ID
    public ResponseEntity<?> findByEmpId(long empId) {
        return new ResponseEntity<>(userRepository.findByEmpId(empId), HttpStatus.OK);
    }
}
