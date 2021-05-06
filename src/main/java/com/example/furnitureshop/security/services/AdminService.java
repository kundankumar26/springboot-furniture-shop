package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.repository.FurnitureRepository;
import com.example.furnitureshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminService {
    @Autowired
    private FurnitureRepository furnitureRepository;

    @Autowired
    private UserRepository userRepository;

    //Get orders that are unchecked
    public ResponseEntity<?> getUncheckedOrders(){
        return new ResponseEntity<>(furnitureRepository.findUncheckedOrders(), HttpStatus.OK);
    }

    //Get accepted or rejected orders
    public ResponseEntity<?> getOldOrders() {
        return new ResponseEntity<>(furnitureRepository.findOldOrders(), HttpStatus.OK);
    }
}
