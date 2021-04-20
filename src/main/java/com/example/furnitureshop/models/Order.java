package com.example.furnitureshop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name="furniture")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long orderId;
    private long empId;
    private String empName;
    @Email
    private String email;
    private String itemRequested;
    private long qty;
    private String shippingAddress;
    private long phnNo;
    private String orderDate;
    private boolean isRejectedByAdmin;


    public Order(){

    }

    public Order(long empId, String empName, @Email String email, String itemRequested, long qty,
                 String shippingAddress, long phnNo, String orderDate,
                 boolean rejectedByAdmin) {
        super();
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.itemRequested = itemRequested;
        this.qty = qty;
        this.shippingAddress = shippingAddress;
        this.phnNo = phnNo;
        this.orderDate = orderDate;
        this.isRejectedByAdmin=rejectedByAdmin;
    }

    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
    public long getEmpId() {
        return empId;
    }
    public void setEmpId(long l) {
        this.empId = l;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getItemRequested() {
        return itemRequested;
    }
    public void setItemRequested(String itemRequested) {
        this.itemRequested = itemRequested;
    }
    public long getQty() {
        return qty;
    }
    public void setQty(long l) {
        this.qty = l;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    public long getPhnNo() {
        return phnNo;
    }
    public void setPhnNo(long phnNo) {
        this.phnNo = phnNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String s) {
        this.orderDate = s;
    }

    public boolean isRejectedByAdmin() {
        return isRejectedByAdmin;
    }

    public void setRejectedByAdmin(boolean isRejectedByAdmin) {
        this.isRejectedByAdmin = isRejectedByAdmin;
    }


}