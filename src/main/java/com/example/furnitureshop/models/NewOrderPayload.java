package com.example.furnitureshop.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javassist.SerialVersionUID;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewOrderPayload {
    //HashMap<productId, Qty>
    //private List<ProductWithQtyPayload> productWithQtyPayload;
    private List<ProductWithQtyPayload> productRequest;
    private String shippingAddress;
    private int phoneNumber;

    public NewOrderPayload(){}

    public NewOrderPayload(List<ProductWithQtyPayload> productRequest, String shippingAddress, int phoneNumber) {
        this.productRequest = productRequest;
        this.shippingAddress = shippingAddress;
        this.phoneNumber = phoneNumber;
    }

    public List<ProductWithQtyPayload> getProductRequest() {
        return productRequest;
    }

    public void setProductRequest(List<ProductWithQtyPayload> productRequest) {
        this.productRequest = productRequest;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
