package com.example.furnitureshop.controllers;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.*;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('VENDOR')")
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private FurnitureService furnitureService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmailSenderService emailSenderService;

    //Get all orders for vendor
    @GetMapping(value = "/")
    public ResponseEntity<?> getOrders() {
        ResponseEntity<?> responseEntity = null;
        try{
            responseEntity = vendorService.getAllOrdersForVendor();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResourceNotFoundException("Cannot get the orders"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    //Change shipping and delivery date
    @PatchMapping(value = "/{orderId}")
    public ResponseEntity<?> updateDateOfOrder(@PathVariable long orderId, @RequestBody EmployeeResponseTable orderDetails) {

        StringBuilder ordersStringBuilder = new StringBuilder();
        try{
            vendorService.updateOrderByVendor(orderId, orderDetails);
//            ordersStringBuilder.append("<tr style=\"text-align: center\"><td>").append(updatedOrder.getOrderId()).append("</td>").append("<td>").append(updatedOrder.getItemRequested()).append("</td>");
//            ordersStringBuilder.append("<td>").append(updatedOrder.getQty()).append("</td>").append("<td>").append(updatedOrder.getShippingAddress()).append("</td>");
//            ordersStringBuilder.append("<td>").append(updatedOrder.getPhnNo()).append("</td>").append("<td>").append(updatedOrder.getShippedDate().substring(0, 11)).append("</td></tr>");

//            javaMailSender.send(GlobalClassForFunctions.sendEmailForOrder(javaMailSender.createMimeMessage(), updatedOrder.getEmail(),
//                    "Order confirmed successfully.", "confirmed", ordersStringBuilder,
//                    "none"));

            emailSenderService.sendConfirmedOrderEmail(orderDetails);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Order cannot be updated"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/{path}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> invalidPath(@PathVariable String path){
        return new ResponseEntity<>(new MessageResponse("Invalid Path"), HttpStatus.NOT_FOUND);
    }
}
