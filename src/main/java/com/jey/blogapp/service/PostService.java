package com.jey.blogapp.service;

import com.jey.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface PostService {
    void save(Post post);
    List<Post> findAll();
    Page<Post> findAll(int pageNo, int pageSize);
    Page<Post> findPostsSortBy(String sortBy, String order, int pageNo, int pageSize);
    List<Post> findPostsWithKeyword(String keyword, int pageNo, int pageSize);
    List<Post> findPostsSortByWithKeyword(String sortBy, String order, String keyword, int pageNo, int pageSize);
    Post findById(int id);
    void deleteById(int id);
}
