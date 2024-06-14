package com.jey.blogapp.service;

import com.jey.blogapp.entity.Comment;
import com.jey.blogapp.entity.Post;

import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> findAll();
    List<Comment> findByPostId(int postId);
    Comment findById(int id);
    void deleteById(int id);
}

