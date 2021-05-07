package com.example.furnitureshop.models;

public class EmployeeRequest {
    private long productId;
    private int qty;
    private String address;
    private String phoneNumber;

    public EmployeeRequest(){ }

    public EmployeeRequest(long productId, int qty, String address, String phoneNumber) {
        this.productId = productId;
        this.qty = qty;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
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
