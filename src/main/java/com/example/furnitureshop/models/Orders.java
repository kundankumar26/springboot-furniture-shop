package com.example.furnitureshop.models;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@Table(name="orders_table")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private long userId;

    private long productId;

    private long addressId;

    private int qty;

    private Date orderDate;

    private Date shippedDate;

    private Date deliveryDate;

    private int isRejectedByAdmin;

    public Orders(){ }

    public Orders(long userId, long productId, long addressId, int qty, Date orderDate, int isRejectedByAdmin) {
        this.userId = userId;
        this.productId = productId;
        this.addressId = addressId;
        this.qty = qty;
        this.orderDate = orderDate;
        this.isRejectedByAdmin = isRejectedByAdmin;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    public int getIsRejectedByAdmin() {
        return isRejectedByAdmin;
    }

    public void setIsRejectedByAdmin(int isRejectedByAdmin) {
        this.isRejectedByAdmin = isRejectedByAdmin;
    }
}
