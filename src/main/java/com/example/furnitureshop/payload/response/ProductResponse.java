package com.example.furnitureshop.payload.response;

import com.example.furnitureshop.models.Comment;
import com.example.furnitureshop.models.Product;
import com.example.furnitureshop.models.User;

import java.util.List;

public class ProductResponse {
    private Product product;
    private List<Comment> commentList;
    private List<User> users;

    public ProductResponse() { }

    public ProductResponse(Product product, List<Comment> commentList, List<User> users) {
        this.product = product;
        this.commentList = commentList;
        this.users = users;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
