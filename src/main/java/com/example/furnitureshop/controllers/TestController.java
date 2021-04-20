package com.example.furnitureshop.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('VENDOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "Employee Content.";
    }

    @GetMapping("/vendor")
    @PreAuthorize("hasRole('VENDOR')")
    public String moderatorAccess() {
        return "Vendor Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

}