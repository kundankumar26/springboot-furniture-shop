package com.example.furnitureshop.controllers;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping(value = "/")
    public ResponseEntity<?> getOrders() {

        ResponseEntity<?> responseEntity = null;
        try{
            responseEntity = furnitureService.getAllOrdersForVendor();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResourceNotFoundException("Cannot get the orders"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PatchMapping(value = "/{orderId}")
    public ResponseEntity<?> updateDateOfOrder(@PathVariable long orderId, @RequestBody Order orderDetails){
        ResponseEntity<?> responseEntity = null;
        try{
            responseEntity = furnitureService.updateOrderByVendor(orderId, orderDetails);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Order cannot be updated"), HttpStatus.BAD_REQUEST);
        }
        javaMailSender.send(GlobalClassForFunctions.sendEmailForOrder("alternate8989@gmail.com", "Order Confirmed", "Hey your order is confirmed"));
        return new ResponseEntity<>(responseEntity, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/{path}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> invalidPath(@PathVariable String path){
        return new ResponseEntity<>(new MessageResponse("Invalid Path"), HttpStatus.NOT_FOUND);
    }
}
