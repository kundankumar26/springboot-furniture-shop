package com.example.furnitureshop.models;

import java.util.List;

public class EmployeeRequest {
    private List<Long> productIds;
    private List<Integer> qty;
    private String address;
    private String phoneNumber;

    public EmployeeRequest(){ }

    public EmployeeRequest(List<Long> productIds, List<Integer> qty, String address, String phoneNumber) {
        this.productIds = productIds;
        this.qty = qty;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Integer> getQty() {
        return qty;
    }

    public void setQty(List<Integer> qty) {
        this.qty = qty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
