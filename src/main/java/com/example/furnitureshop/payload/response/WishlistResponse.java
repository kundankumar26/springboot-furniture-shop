package com.example.furnitureshop.payload.response;

import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.Wishlist;

import java.util.List;

public class WishlistResponse {
    private List<Wishlist> wishList;
    private List<Product> productList;

    public WishlistResponse() { }

    public WishlistResponse(List<Wishlist> wishlist, List<Product> productList) {
        this.wishList = wishlist;
        this.productList = productList;
    }

    public List<Wishlist> getWishList() {
        return wishList;
    }

    public void setWishList(List<Wishlist> wishList) {
        this.wishList = wishList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
