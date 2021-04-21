package com.example.furnitureshop.controllers;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private FurnitureService furnitureService;

    @GetMapping(value = "/")
    public ModelAndView getOrders() {
        return new ModelAndView("redirect:/employee/" + getCurrentEmployeeId() + "/");
    }

    @GetMapping(value = "/{empId}")
    public ResponseEntity<?> getOrderByEmpId(@PathVariable long empId){
        long currentEmployeeId = getCurrentEmployeeId();
        if(empId != currentEmployeeId){
            return new ResponseEntity<>(new MessageResponse("Unauthorised Request"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(furnitureService.getOrderByEmpId(currentEmployeeId), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        long currentEmployeeId = getCurrentEmployeeId();
        User user = (User) furnitureService.findByEmpId(currentEmployeeId).getBody();
        if(user == null){
            return new ResponseEntity<>(new MessageResponse("Order cannot be created for employee with id: " + currentEmployeeId), HttpStatus.BAD_REQUEST);
        }
        order.setEmpId(currentEmployeeId);
        order.setEmpName(user.getEmpFirstName() + " " + user.getEmpLastName());
        order.setEmail(user.getEmail());
        return new ResponseEntity<>(furnitureService.createOrder(order), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable long orderId){
        return new ResponseEntity<>(furnitureService.deleteOrder(orderId), HttpStatus.OK);
    }

//    @RequestMapping(value = "/{path}", method = {RequestMethod.GET, RequestMethod.POST})
//    public ResponseEntity<?> invalidPath(@PathVariable String path){
//        System.out.println("invalid path");
//        return new ResponseEntity<>(new MessageResponse("Invalid Path"), HttpStatus.BAD_REQUEST);
//    }

    private long getCurrentEmployeeId(){
        String username = GlobalClassForFunctions.getUserNameFromToken();
        return Long.parseLong(String.valueOf(furnitureService.findEmpIdByUsername(username).getBody()));
    }
}
