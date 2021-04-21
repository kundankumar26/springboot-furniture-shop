package com.example.furnitureshop.controllers;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


import com.example.furnitureshop.models.ERole;
import com.example.furnitureshop.models.Role;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.payload.request.LoginRequest;
import com.example.furnitureshop.payload.request.SignupRequest;
import com.example.furnitureshop.payload.response.JwtResponse;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.repository.RoleRepository;
import com.example.furnitureshop.repository.UserRepository;
import com.example.furnitureshop.security.jwt.JwtUtils;
import com.example.furnitureshop.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        System.out.println(loginRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        for(String role: roles){
            System.out.println(role + " " + jwt);
        }

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getEmpUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if(userRepository.existsByEmpId(signUpRequest.getEmpId())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Employee Id is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getEmpId(),
                signUpRequest.getEmpFirstName().substring(0, 1).toUpperCase() + signUpRequest.getEmpFirstName().substring(1).toLowerCase(),
                signUpRequest.getEmpLastName().substring(0, 1).toUpperCase() + signUpRequest.getEmpLastName().substring(1).toLowerCase(),
                signUpRequest.getEmpUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getEmpPassword()));

        String currentUserRole = signUpRequest.getRole();
        Set<String> strRoles = Collections.singleton(currentUserRole==null ? "false" : currentUserRole);
        Set<Role> roles = new HashSet<>();
        System.out.println(signUpRequest.getRole());

        strRoles.forEach(role -> {
            if (role.equals("vendor")) {
                Role vendorRole = roleRepository.findByName(ERole.ROLE_VENDOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(vendorRole);
            } else if (role.equals("employee")) {
                Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                throw new RuntimeException("Error: Role does not exist.");
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}

