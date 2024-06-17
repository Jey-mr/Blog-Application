package com.jey.blogapp.service;

import com.jey.blogapp.entity.Post;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface PostService {
    void save(Post post);
    List<Post> findAll();
    List<Post> findPostsSortBy(String sortBy, String order);
    List<Post> findPostsWithKeyword(String keyword);
    List<Post> findPostsSortByWithKeyword(String sortBy, String order, String keyword);
    Post findById(int id);
    void deleteById(int id);
}
