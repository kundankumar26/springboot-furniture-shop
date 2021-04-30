package com.example.furnitureshop.controllers;

import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FurnitureService furnitureService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getOrders() {
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = furnitureService.getAllOrders();
        } catch(Exception e){
            return new ResponseEntity<>(new ResourceNotFoundException("Cannot get the orders"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @GetMapping(value = "/page={pageNumber}&sort={sortBy}")
    public ResponseEntity<?> getOrders(@PathVariable int pageNumber, @PathVariable String sortBy) {
        ResponseEntity<?> responseEntity;
        try{
            Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(sortBy));
            responseEntity = furnitureService.getAllOrdersByPages(pageable);
        } catch(Exception e){
            return new ResponseEntity<>(new ResourceNotFoundException("Cannot get the orders"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PatchMapping(value = "/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @RequestBody Order orderDetails){
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = furnitureService.updateOrderByAdmin(orderId, orderDetails);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Order cannot be updated"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId){
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = furnitureService.deleteOrder(orderId);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Order cannot be deleted"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.ACCEPTED);
    }
}
