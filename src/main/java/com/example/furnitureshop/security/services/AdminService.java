package com.example.furnitureshop.security.services;

import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.payload.response.AdminResponse;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FurnituresRepository furnituresRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    //Get orders that are unchecked
    public ResponseEntity<?> getUncheckedOrders(){
        List<Orders> ordersList = furnituresRepository.getAllOrders();
        Set<Long> productIds = new HashSet<>();
        Set<Long> userIds = new HashSet<>();
        for(Orders orders: ordersList){
            productIds.add(orders.getProductId());
            userIds.add(orders.getUserId());
        }
        List<Product> productList = productRepository.findProductsForCart(productIds);
        List<User> userList = userRepository.findUsersByIds(userIds);
        return new ResponseEntity<>(new AdminResponse(ordersList, userList, productList), HttpStatus.OK);
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
