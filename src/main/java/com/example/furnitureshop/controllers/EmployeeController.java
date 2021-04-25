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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('EMPLOYEE')")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping(value = "/")
    public Object getOrders() {
        long employeeId = -1;
        try{
            employeeId = getCurrentEmployeeId();
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Not authorised"), HttpStatus.UNAUTHORIZED);
        }
        return new ModelAndView("redirect:/employee/" + employeeId + "/");
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
    public ResponseEntity<?> createOrder(@RequestBody List<Order> orderDetails) {
        long currentEmployeeId = getCurrentEmployeeId();
        User user = (User) furnitureService.findByEmpId(currentEmployeeId).getBody();
        if(user == null){
            return new ResponseEntity<>(new MessageResponse("Order cannot be created for employee with id: " + currentEmployeeId), HttpStatus.BAD_REQUEST);
        }
        StringBuilder ordersStringBuilder = new StringBuilder();
        for(Order order: orderDetails){
            order.setEmpId(currentEmployeeId);
            order.setEmpName(user.getEmpFirstName() + " " + user.getEmpLastName());
            order.setEmail(user.getEmail());
            order.setIsRejectedByAdmin(0);
            order.setOrderDate(GlobalClassForFunctions.getCurrentDateAndTime(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime())));
            ordersStringBuilder.append("<tr style=\"text-align: center\"><td>").append(order.getOrderId()).append("</td>").append("<td>").append(order.getItemRequested()).append("</td>");
            ordersStringBuilder.append("<td>").append(order.getQty()).append("</td>").append("<td>").append(order.getShippingAddress()).append("</td>");
            ordersStringBuilder.append("<td>").append(order.getPhnNo()).append("</td>").append("<td>").append(order.getOrderDate().substring(0, 11)).append("</td></tr>");
        }

        try{
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//            GlobalClassForFunctions.sendEmailForOrderAlternate(helper, "alternate8989@gmail.com", "Order placed successfully.",
//                    ordersStringBuilder, user.getEmpFirstName() + " " + user.getEmpLastName());
//            javaMailSender.send(mimeMessage);
            javaMailSender.send(GlobalClassForFunctions.sendEmailForOrder(javaMailSender.createMimeMessage(), "alternate8989@gmail.com", "Order placed successfully.",
                    "placed", ordersStringBuilder, user.getEmpFirstName() + " " + user.getEmpLastName()));
        } catch(Exception e){
            e.printStackTrace();
        }
        ResponseEntity<?> createdOrders;
        try{
            createdOrders = furnitureService.createOrder(orderDetails);
        } catch(Exception e) {
            return new ResponseEntity<>(new MessageResponse("Order cannot be created"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable long orderId){
        try{
            furnitureService.deleteOrder(orderId);
        } catch(Exception e) {
            return new ResponseEntity<>(new MessageResponse("Order not found with id: " + orderId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("Order deleted successfully."), HttpStatus.OK);
    }

    @RequestMapping(value = "/{path}/{subpath}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> invalidPath(@PathVariable String path){
        return new ResponseEntity<>(new MessageResponse("Invalid path"), HttpStatus.BAD_REQUEST);
    }

    private long getCurrentEmployeeId(){
        String username = GlobalClassForFunctions.getUserNameFromToken();
        return Long.parseLong(String.valueOf(furnitureService.findEmpIdByUsername(username).getBody()));
    }
}
