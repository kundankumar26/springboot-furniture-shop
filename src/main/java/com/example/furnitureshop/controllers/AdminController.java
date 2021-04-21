package com.example.furnitureshop.controllers;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/")
    public ResponseEntity<?> getOrders() {
        return new ResponseEntity<>(furnitureService.getAllOrders(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @RequestBody Order orderDetails){
        return new ResponseEntity<>(furnitureService.updateOrder(orderId, orderDetails), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(furnitureService.deleteOrder(orderId), HttpStatus.ACCEPTED);
    }
}
