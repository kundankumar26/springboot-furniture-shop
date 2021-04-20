package com.example.furnitureshop.controllers;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private long currentEmployeeId;
    @Autowired
    private FurnitureService furnitureService;

//    @GetMapping(value = "/")
//    public String getord(){
//        return "its employee orders";
//    }


    @GetMapping(value = "/")
    public ModelAndView getOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        currentEmployeeId = Long.parseLong(String.valueOf(furnitureService.findEmpIdByUsername(username).getBody()));
        return new ModelAndView("redirect:/employee/" + currentEmployeeId + "/");
    }

    @GetMapping(value = "/{empId}")
    public List<Order> getOrderByEmpId(@PathVariable Long empId){
        return furnitureService.getOrderByEmpId(empId);
    }

    @PostMapping(value = "/")
    public void createOrder(@RequestBody Order order) {
        User user = furnitureService.findByEmpId(currentEmployeeId).getBody();
        assert user != null;
        order.setEmpName(user.getEmpFirstName() + user.getEmpLastName());
        order.setEmail(user.getEmail());

        System.out.println(user.getEmail() + user.getEmpId());
        //return furnitureService.createOrder(order);
    }
}
