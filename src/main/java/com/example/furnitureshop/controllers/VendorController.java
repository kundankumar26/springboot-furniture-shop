package com.example.furnitureshop.controllers;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private FurnitureService furnitureService;

//    @GetMapping(value = "/")
//    public String getorder(){
//        return "its vendor orders";
//    }

    @GetMapping(value = "/")
    public List<Order> getOrders() {
        return furnitureService.getAllOrders();
    }
}
