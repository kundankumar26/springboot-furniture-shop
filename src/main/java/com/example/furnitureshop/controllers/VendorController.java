package com.example.furnitureshop.controllers;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private FurnitureService furnitureService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getOrders() {
        return new ResponseEntity<>(furnitureService.getAllOrdersForVendor(), HttpStatus.OK);
    }

    @PatchMapping(value = "/{orderId}")
    public ResponseEntity<?> updateDateOfOrder(@PathVariable long orderId, @RequestBody Order orderDetails){
        return new ResponseEntity<>(furnitureService.updateOrderByVendor(orderId, orderDetails), HttpStatus.ACCEPTED);
    }
}
