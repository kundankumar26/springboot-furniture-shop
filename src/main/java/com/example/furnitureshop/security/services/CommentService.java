package com.example.furnitureshop.security.services;

import com.example.furnitureshop.models.Comment;
import com.example.furnitureshop.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //Get all comments by product id
    public List<Comment> getCommentsByProduct(long productId){
        return commentRepository.getCommentsByProduct(productId);
    }

    //Create comment for a product
    public Comment createComment(long userId, Comment comment){
        return commentRepository.save(new Comment(userId, comment.getProductId(), comment.getComment(), comment.getRating()));
    }

    //Update comment by user
    public Comment updateCommentById(long commentId, Comment commentDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Cannot find comment"));
        comment.setComment(commentDetails.getComment());
        comment.setRating(commentDetails.getRating());
        return commentRepository.save(comment);
    }

    public Comment getCommentByUserId(long userId, long productId){
        return commentRepository.findIfRated(userId, productId);
    }

    public double getRatingFromComments(long productId) {
        return commentRepository.getRatingFromComments(productId);
    }
}
