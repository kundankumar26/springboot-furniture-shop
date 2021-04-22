package com.example.furnitureshop.controllers;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping(value = "/")
    public ModelAndView getOrders() {
        return new ModelAndView("redirect:/employee/" + getCurrentEmployeeId() + "/");
    }

    @GetMapping(value = "/{empId}")
    public ResponseEntity<?> getOrderByEmpId(@PathVariable String empId){
        long empIdFromPath, currentEmployeeId;
        try{
            empIdFromPath = Long.parseLong(empId);
            currentEmployeeId = getCurrentEmployeeId();
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("User Not Found with id: " + empId), HttpStatus.NOT_FOUND);
        }
        if(empIdFromPath != currentEmployeeId){
            return new ResponseEntity<>(new MessageResponse("User Not Found with id: " + empIdFromPath), HttpStatus.NOT_FOUND);
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
        javaMailSender.send(GlobalClassForFunctions.sendEmailForOrder("alternate8989@gmail.com", "Order Created", "You ordered a product"));
        return new ResponseEntity<>(furnitureService.createOrder(order), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable long orderId){
        return new ResponseEntity<>(furnitureService.deleteOrder(orderId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{path}/{subpath}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> invalidPath(@PathVariable String path){
        return new ResponseEntity<>(new MessageResponse("Invalid Path"), HttpStatus.BAD_REQUEST);
    }

    private long getCurrentEmployeeId(){
        String username = GlobalClassForFunctions.getUserNameFromToken();
        return Long.parseLong(String.valueOf(furnitureService.findEmpIdByUsername(username).getBody()));
    }
}
