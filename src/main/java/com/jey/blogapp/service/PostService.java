package com.jey.blogapp.service;

import com.jey.blogapp.entity.Post;

import java.util.List;


public interface PostService {
    void save(Post post);
    List<Post> findAll();
    List<Post> findPostsWithKeyword(String keyword);
    Post findById(int id);
    void deleteById(int id);
}
