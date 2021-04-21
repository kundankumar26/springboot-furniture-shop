package com.example.furnitureshop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull
    @NotBlank
    private long qty;
    @NotNull
    @NotBlank
    private String shippingAddress;
    @NotBlank
    private String shippedDate;
    @NotNull
    @NotBlank
    private long phnNo;
    private String orderDate;
    //0 - For employee and admin
    //1 - For employee, vendor and admin
    //2 - For admin
    private int isRejectedByAdmin;

    public Order(){

    }

    public Order(long empId, String empName, @Email String email,
                 String itemRequested, @NotNull @NotBlank long qty,
                 @NotNull @NotBlank String shippingAddress,
                 @NotBlank String shippedDate,
                 @NotNull @NotBlank long phnNo, String orderDate, int isRejectedByAdmin) {
        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.itemRequested = itemRequested;
        this.qty = qty;
        this.shippingAddress = shippingAddress;
        this.shippedDate = shippedDate;
        this.phnNo = phnNo;
        this.orderDate = orderDate;
        this.isRejectedByAdmin = isRejectedByAdmin;
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
    public String getShippedDate() {
        return shippedDate;
    }
    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
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
    public int getIsRejectedByAdmin() {
        return isRejectedByAdmin;
    }
    public void setIsRejectedByAdmin(int isRejectedByAdmin) {
        this.isRejectedByAdmin = isRejectedByAdmin;
    }
}