package com.example.furnitureshop.payload.response;

import com.example.furnitureshop.models.Cart;
import com.example.furnitureshop.models.Product;

import java.util.List;

public class CartResponse {
    private List<Cart> cartList;
    private List<Product> productList;

    public CartResponse() { }

    public CartResponse(List<Cart> cartList, List<Product> productList) {
        this.cartList = cartList;
        this.productList = productList;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
