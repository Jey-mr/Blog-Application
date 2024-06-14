package com.jey.blogapp.dao;

import com.jey.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select c from Comment c where c.post.id = ?1")
    List<Comment> findByPostId(int postId);
}
