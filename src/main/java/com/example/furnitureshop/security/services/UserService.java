package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.User;
import com.example.furnitureshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserById(long userId){
        User user = null;
        try {
            user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        } catch(Exception e){
            return null;
        }
        return user;
    }
}
