package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Role;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.repository.RoleRepository;
import com.example.furnitureshop.repository.UserRepository;
import com.example.furnitureshop.security.jwt.AuthEntryPointJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User findUserById(long userId){
        User user = null;
        try {
            user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        } catch(Exception e){
            return null;
        }
        return user;
    }

    public List<User> findUsersByIds(Set<Long> userIds) {
        List<User> userList = userRepository.findUsersByIds(userIds);
        for(User user: userList){
            user.setEmpPassword(null);
        }
        return userList;
    }

    public void isRoleTableEmpty() {
        List<Role> roles = roleRepository.isEmptyTable();
        if(roles.size() != 3){
            logger.error(roles + " " + roles.size());
            roleRepository.createRoles();
        }
    }
}
