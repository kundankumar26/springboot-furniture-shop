package com.example.furnitureshop.controllers;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.models.Comment;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.payload.response.ProductResponse;
import com.example.furnitureshop.security.services.CommentService;
import com.example.furnitureshop.security.services.ProductService;
import com.example.furnitureshop.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/")
    public ResponseEntity<?> findAllProducts() {
        ResponseEntity<?> responseEntity;
        try {
            long userId = GlobalClassForFunctions.getUserIdFromToken();
            responseEntity = productService.findProductsForUser(userId);
        } catch(Exception e){
            try {
                responseEntity = productService.findAllProducts();
            } catch (Exception e1){
                return new ResponseEntity<>(new MessageResponse("Failed to get the products"), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable long productId) {
        ResponseEntity<?> responseEntity;
        try {
            Product product = productService.findProductById(productId);
            List<Comment> commentList = commentService.getCommentsByProduct(productId);
            Set<Long> userIds = new HashSet<>();
            for(Comment comment: commentList){
                userIds.add(comment.getUserId());
            }
            List<User> users = userService.findUsersByIds(userIds);
            responseEntity = new ResponseEntity<>(new ProductResponse(product, commentList, users), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot get the product"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @GetMapping(value = "/{productId}/comments")
    public ResponseEntity<?> checkIfRatedTheProduct(@PathVariable(value = "productId") String productid){
        ResponseEntity<?> responseEntity = null;
        try {
            long productId = Long.parseLong(productid);
            long userId = GlobalClassForFunctions.getUserIdFromToken();
            Comment comment = commentService.getCommentByUserId(userId, productId);
            if(comment == null){
                throw new RuntimeException("Cannot get the comment by user");
            }
            responseEntity = new ResponseEntity<>(comment, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot get the comment by user"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PatchMapping(value = "/{productId}/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable(value = "commentId") String commentid, @RequestBody Comment comment){
        ResponseEntity<?> responseEntity = null;
        try{
            long commentId = Long.parseLong(commentid);
            responseEntity = new ResponseEntity<>(commentService.updateCommentById(commentId, comment), HttpStatus.OK);
            productService.updateProductRating(comment.getProductId(), comment.getRating());
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot update comment"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/{productId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> addCommentToProduct(@RequestBody Comment comment) {
        ResponseEntity<?> responseEntity;
        try {
            long userId = GlobalClassForFunctions.getUserIdFromToken();
            responseEntity = new ResponseEntity<>(commentService.createComment(userId, comment), HttpStatus.OK);
            productService.updateProductRating(comment.getProductId(), comment.getRating());
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot create the comment"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        ResponseEntity<?> responseEntity;
        try {
            long userId = GlobalClassForFunctions.getUserIdFromToken();
            responseEntity = productService.createProduct(userId, product);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Cannot create the product by user"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseEntity, HttpStatus.OK);
    }
}
