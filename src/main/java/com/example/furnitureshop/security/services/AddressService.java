package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Address;
import com.example.furnitureshop.models.EmployeeRequest;
import com.example.furnitureshop.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(long userId, EmployeeRequest employeeRequest){
        Address address;
        try {
            address = addressRepository.save(new Address(userId, employeeRequest.getAddress(),
                    employeeRequest.getPhoneNumber()));
        } catch(Exception e){
            return null;
        }
        return address;
    }
}
