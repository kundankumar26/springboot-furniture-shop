package com.example.furnitureshop.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "admin_table")
public class AdminResponse {
    @Id
    private long orderId;
    private int qty;
    private int isRejectedByAdmin;
    private long id;
    private long empId;
    private String empFirstName;
    private String empLastName;
    private String email;
    private String productName;
    private int productPrice;
    private int productQty;

    public AdminResponse(){}

    public AdminResponse(long orderId, int qty, int isRejectedByAdmin, long id, long empId,
                         String empFirstName, String empLastName, String email, String productName,
                         int productPrice, int productQty) {
        this.orderId = orderId;
        this.qty = qty;
        this.isRejectedByAdmin = isRejectedByAdmin;
        this.id = id;
        this.empId = empId;
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.email = email;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getIsRejectedByAdmin() {
        return isRejectedByAdmin;
    }

    public void setIsRejectedByAdmin(int isRejectedByAdmin) {
        this.isRejectedByAdmin = isRejectedByAdmin;
    }

    public long getUserId() {
        return id;
    }

    public void setUserId(long userId) {
        this.id = userId;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}
