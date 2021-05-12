package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comments WHERE product_id=:productId", nativeQuery = true)
    List<Comment> getCommentsByProduct(@Param(value = "productId") long productId);

    @Query(value = "SELECT * FROM comments WHERE user_id=:userId and product_id=:productId", nativeQuery = true)
    Comment findIfRated(long userId, long productId);
}
