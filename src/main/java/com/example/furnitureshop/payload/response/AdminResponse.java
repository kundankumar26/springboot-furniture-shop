package com.example.furnitureshop.payload.response;

import com.example.furnitureshop.models.Orders;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.User;

import java.util.List;

public class AdminResponse {
    private List<Orders> orders;
    private List<User> users;
    private List<Product> product;

    public AdminResponse(){}

    public AdminResponse(List<Orders> orders, List<User> users, List<Product> product) {
        this.orders = orders;
        this.users = users;
        this.product = product;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<User> getUser() {
        return users;
    }

    public void setUser(List<User> users) {
        this.users = users;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
