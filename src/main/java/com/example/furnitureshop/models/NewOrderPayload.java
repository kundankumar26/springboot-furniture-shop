package com.example.furnitureshop.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javassist.SerialVersionUID;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewOrderPayload extends SerialVersionUID implements Serializable {
    //HashMap<productId, Qty>
    private Map<String, String> productsIdWithQty;
    private String shippingAddress;
    private int phoneNumber;

    public NewOrderPayload(){}

    public NewOrderPayload(Map<String, String> productsIdWithQty, String shippingAddress, int phoneNumber) {
        this.productsIdWithQty = productsIdWithQty;
        this.shippingAddress = shippingAddress;
        this.phoneNumber = phoneNumber;
    }

    public Map<String, String> getProductsIdWithQty() {
        return productsIdWithQty;
    }

    public void setProductsIdWithQty(Map<String, String> productsIdWithQty) {
        this.productsIdWithQty = productsIdWithQty;
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
