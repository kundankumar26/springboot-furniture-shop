package com.example.furnitureshop.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "employee_table")
public class EmployeeResponseTable {
//    private long orderId;
//    private String userFirstName;
//    private String userLastName;
//    private String userEmail;
//    private int isRejectedByAdmin;
//    private String productName;
//    private String productCategory;
//    private int productPrice;
//    private int productRating;
//    private int qty;
//    private String shippingAddress;
//    private String phoneNumber;
//    private Date orderDate;
//    private Date shippedDate;
//    private Date DeliveryDate;

    @Id
    private long orderId;
    private String empFirstName;
    private String empLastName;
    private String email;
    private int isRejectedByAdmin;
    private String productName;
    private String productCategory;
    private int productPrice;
    private int productRating;
    private long productQty;
    private String productImageUrl;
    private int qty;
    private String address;
    private int phoneNumber;
    private Date orderDate;
    private Date shippedDate;
    private Date deliveryDate;

    public EmployeeResponseTable(){}

    public EmployeeResponseTable(long orderId, String empFirstName, String empLastName, String email, int isRejectedByAdmin,
                                 String productName, String productCategory, int productPrice, int productRating,
                                 long productQty, String productImageUrl, int qty, String address, int phoneNumber,
                                 Date orderDate, Date shippedDate, Date deliveryDate) {
        this.orderId = orderId;
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.email = email;
        this.isRejectedByAdmin = isRejectedByAdmin;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productRating = productRating;
        this.productQty = productQty;
        this.productImageUrl = productImageUrl;
        this.qty = qty;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        this.deliveryDate = deliveryDate;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public int getIsRejectedByAdmin() {
        return isRejectedByAdmin;
    }

    public void setIsRejectedByAdmin(int isRejectedByAdmin) {
        this.isRejectedByAdmin = isRejectedByAdmin;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductRating() {
        return productRating;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }

    public long getProductQty() {
        return productQty;
    }

    public void setProductQty(long productQty) {
        this.productQty = productQty;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
