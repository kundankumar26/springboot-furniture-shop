package com.example.furnitureshop.payload.request;

import java.util.ArrayList;
import java.util.List;

public class CartRequestPayload {
    private ArrayList<Integer> productId = new ArrayList<>();
    private ArrayList<Integer> qty = new ArrayList<>();
    private String address;
    private String phone;

    public CartRequestPayload(){ }

    public CartRequestPayload(ArrayList<Integer> productId, ArrayList<Integer> qty, String address, String phone) {
        this.productId = productId;
        this.qty = qty;
        this.address = address;
        this.phone = phone;
    }

    public ArrayList<Integer> getIds() {
        return productId;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.productId = ids;
    }

    public ArrayList<Integer> getQty() {
        return qty;
    }

    public void setQty(ArrayList<Integer> qty) {
        this.qty = qty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
