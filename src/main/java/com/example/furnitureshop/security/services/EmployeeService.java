package com.example.furnitureshop.security.services;

import com.example.furnitureshop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Get all the orders created by the current employee
    public ResponseEntity<?> findOrdersByUserId(long userId){
        return new ResponseEntity<>(employeeRepository.findAllOrdersForEmployee(userId), HttpStatus.OK);
    }
}
