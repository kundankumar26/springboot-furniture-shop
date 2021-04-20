package com.example.furnitureshop.controllers;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FurnitureService furnitureService;

//    @GetMapping(value = "/")
//    public String getorde(){
//        return "its admin orders";
//    }

    @GetMapping(value = "/")
    public List<Order> getOrders() {
        return furnitureService.getAllOrders();
    }

    @PatchMapping(value = "/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Map<Object, Object> order){
        return furnitureService.updateOrder(orderId, order);
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId){
        return furnitureService.deleteOrder(orderId);
    }
}
