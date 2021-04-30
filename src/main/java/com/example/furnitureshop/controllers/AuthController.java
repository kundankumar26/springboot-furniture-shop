package com.example.furnitureshop.controllers;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.ConfirmationToken;
import com.example.furnitureshop.models.ERole;
import com.example.furnitureshop.models.Role;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.payload.request.LoginRequest;
import com.example.furnitureshop.payload.request.SignupRequest;
import com.example.furnitureshop.payload.response.JwtResponse;
import com.example.furnitureshop.payload.response.MessageResponse;
import com.example.furnitureshop.repository.ConfirmationTokenRepository;
import com.example.furnitureshop.repository.RoleRepository;
import com.example.furnitureshop.repository.UserRepository;
import com.example.furnitureshop.security.jwt.JwtUtils;
import com.example.furnitureshop.security.services.EmailSenderService;
import com.example.furnitureshop.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        System.out.println(loginRequest.getUsername());
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByEmployeeUsername(loginRequest.getUsername());
        if(confirmationToken != null){
            System.out.println("Account not verified.");
            return new ResponseEntity<>(new MessageResponse("Account not verified."), HttpStatus.FORBIDDEN);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

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
        User user = null;
        try {
            user = new User(signUpRequest.getEmpId(),
                    signUpRequest.getEmpFirstName().substring(0, 1).toUpperCase() + signUpRequest.getEmpFirstName().substring(1).toLowerCase(),
                    signUpRequest.getEmpLastName().substring(0, 1).toUpperCase() + signUpRequest.getEmpLastName().substring(1).toLowerCase(),
                    signUpRequest.getEmpUsername().toLowerCase(Locale.ENGLISH), signUpRequest.getEmail(),
                    passwordEncoder.encode(signUpRequest.getEmpPassword()));
            user.setIsEnabled(false);
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("Error in registering user."), HttpStatus.NOT_ACCEPTABLE);
        }

        String currentUserRole = signUpRequest.getRole().toLowerCase(Locale.ROOT);
        Set<String> strRoles = Collections.singleton(currentUserRole == null ? "false" : currentUserRole);
        Set<Role> roles = new HashSet<>();

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

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete your Registration!");
        mailMessage.setFrom("alternate8991@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        System.out.println("new user created" + user);
        return new ResponseEntity<>(new MessageResponse("User registered successfully!"), HttpStatus.CREATED);
    }

    @GetMapping(value="/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        try{
            long milliSeconds = new Date(new Date(System.currentTimeMillis()).getTime() - token.getCreatedDate().getTime()).getTime();
            long diffInMinutes = TimeUnit.MINUTES.convert(milliSeconds, TimeUnit.MILLISECONDS);

            //System.out.println(new SimpleDateFormat("HH:mm:ss").format(token.getCreatedDate()));
            //System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));

            if(diffInMinutes <= 10) {
                User user = token.getUser();
                System.out.println("confirmed account " + user.getEmail());
                user.setIsEnabled(true);
                userRepository.save(user);
                confirmationTokenRepository.delete(token);
            }
            else{
                throw new RuntimeException();
            }
        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse("The link is invalid or broken!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("Account verified."), HttpStatus.OK);
    }
}

