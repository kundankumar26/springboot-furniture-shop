package com.example.furnitureshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @NotBlank
    private String productCategory;

    @NotBlank
    private String productName;

    @NotNull
    private int productPrice;

    @NotBlank
    private String productDescription;

    @NotNull
    private long productQty;

    @NotNull
    private double productRating;

    @NotBlank
    private String productImageUrl;

    public Product(){}

    public Product(String productCategory, String productName, int productPrice, String productDescription, long productQty,
                   double productRating, String productImageUrl) {
        this.productCategory = productCategory;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productQty = productQty;
        this.productRating = productRating;
        this.productImageUrl = productImageUrl;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public long getProductQty() {
        return productQty;
    }

    public void setProductQty(long productQty) {
        this.productQty = productQty;
    }

    public double getProductRating() {
        return productRating;
    }

    public void setProductRating(double productRating) {
        this.productRating = productRating;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
