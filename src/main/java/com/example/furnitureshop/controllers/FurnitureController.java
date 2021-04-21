package com.example.furnitureshop.controllers;

import java.util.List;
import java.util.Map;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/test")
public class FurnitureController {

    @Autowired
    private FurnitureService furnitureService;
//
//    @GetMapping(value = "/")
//    public List<Order> getOrders() {
//        return (List<Order>) furnitureService.getAllOrders();
//    }
//
//    @PostMapping(value = "/")
//    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//        return furnitureService.createOrder(order);
//    }
//
//    @GetMapping(value = "/emp/{empId}")
//    public List<Order> getOrderByEmpId(@PathVariable Long empId){
//        return (List<Order>) furnitureService.getOrderByEmpId(empId);
//    }
//
//    @GetMapping(value = "/{orderId}")
//    public ResponseEntity<Order> getOneOrder(@PathVariable Long orderId) {
//        return furnitureService.getSingleOrder(orderId);
//    }
//
//    @PatchMapping(value = "/{orderId}")
//    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Map<Object, Object> order){
//        return furnitureService.updateOrder(orderId, order);
//    }

//    @DeleteMapping(value = "/{orderId}")
//    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId){
//        return furnitureService.deleteOrder(orderId);
//    }

}