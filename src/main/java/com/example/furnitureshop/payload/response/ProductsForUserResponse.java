package com.example.furnitureshop.payload.response;

import com.example.furnitureshop.models.Cart;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.Wishlist;

import java.util.List;

public class ProductsForUserResponse {
    private List<Product> productList;
    private List<Cart> cartList;
    private List<Wishlist> wishlists;

    public ProductsForUserResponse(){ }

    public ProductsForUserResponse(List<Product> productList, List<Cart> cartList, List<Wishlist> wishlists) {
        this.productList = productList;
        this.cartList = cartList;
        this.wishlists = wishlists;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }
}
