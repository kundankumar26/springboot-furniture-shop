package com.example.furnitureshop.controllers;

import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.security.services.AdminService;
import com.example.furnitureshop.security.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AdminService adminService;

    @Autowired
    private ProductService productService;


    //Get unchecked orders
    @GetMapping(value = "/")
    public ResponseEntity<?> getUncheckedOrders() {
        ResponseEntity<?> responseEntity;
        try {
            responseEntity = adminService.getUncheckedOrders();
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot get the orders"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    //Get accepted or rejected orders
    @GetMapping(value = "/view-all")
    public ResponseEntity<?> getPreviousOrders() {
        ResponseEntity<?> responseEntity;
        try {
            responseEntity = adminService.getOldOrders();
        } catch(Exception e) {
            return new ResponseEntity<>(new MessageResponse("Cannot get the orders"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    //Update qty and is rejected by admin field
    @PatchMapping(value = "/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @RequestBody Orders orderDetails){
        ResponseEntity<?> responseEntity;
        try{
            if(orderDetails.getIsRejectedByAdmin() == 1) {
                Product product = productService.findProductById(orderDetails.getProductId());
                if (product == null || product.getProductQty() < orderDetails.getQty()) {
                    return new ResponseEntity<>(new MessageResponse("Product qty cannot be satisfied."), HttpStatus.NOT_FOUND);
                }
                product.setProductQty(product.getProductQty() - orderDetails.getQty());
                productService.updateProductQty(product);
            }
            responseEntity = adminService.updateOrderByAdmin(orderId, orderDetails);

        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Order cannot be updated"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    //Delete order with this id
    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId){
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = adminService.deleteOrder(orderId);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Order cannot be deleted"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

//    @GetMapping(value = "/page={pageNumber}&sort={sortBy}")
//    public ResponseEntity<?> getOrders(@PathVariable int pageNumber, @PathVariable String sortBy) {
//        ResponseEntity<?> responseEntity;
//        try{
//            Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by(sortBy));
//            responseEntity = furnitureService.getAllOrdersByPages(pageable);
//        } catch(Exception e){
//            return new ResponseEntity<>(new ResourceNotFoundException("Cannot get the orders"), HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
//    }


}
