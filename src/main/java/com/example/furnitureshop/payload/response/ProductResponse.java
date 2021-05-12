package com.example.furnitureshop.payload.response;

import com.example.furnitureshop.models.Comment;
import com.example.furnitureshop.models.Product;
import java.util.List;

public class ProductResponse {
    private Product product;
    private List<Comment> commentList;

    public ProductResponse() { }

    public ProductResponse(Product product, List<Comment> commentList) {
        this.product = product;
        this.commentList = commentList;
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
}
