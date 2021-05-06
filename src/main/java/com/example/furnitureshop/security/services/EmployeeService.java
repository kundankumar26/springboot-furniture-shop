package com.example.furnitureshop.security.services;

import com.example.furnitureshop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void findbyid(){
        Object object = employeeRepository.findAllOrdersByEmployee();
        System.out.println(object.toString());
    }
}
