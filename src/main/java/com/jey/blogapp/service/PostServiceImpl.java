package com.jey.blogapp.service;

import com.jey.blogapp.dao.PostRepository;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findPostsSortBy(String sortBy, String order, int pageNo, int pageSize) {
        Sort sort = null;
        Pageable pageable = null;

        if(order.equalsIgnoreCase("asc")  ||  order == null) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        pageable = PageRequest.of(pageNo, pageSize, sort);
        return postRepository.findAll(pageable);
    }

    @Override
    public List<Post> findPostsWithKeyword(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return postRepository.findPostsWithKeyword(keyword, pageable);
    }

    @Override
    public List<Post> findPostsSortByWithKeyword(String sortBy, String order, String keyword, int pageNo, int pageSize) {
        Sort sort;
        Pageable pageable;

        if(order.equalsIgnoreCase("asc")  ||  order == null) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        pageable = PageRequest.of(pageNo, pageSize, sort);

        return postRepository.findPostsSortByWithKeyword(keyword, pageable);
    }

    @Override
    public Post findById(int id) {
        Optional<Post> result = postRepository.findById(id);
        Post post = null;

        if(result.isPresent()) {
            post = result.get();
        }

        return post;
    }

    @Override
    public void deleteById(int id) {
        Post post = findById(id);
        postRepository.delete(post);
    }
}
