package com.jey.blogapp.service;

import com.jey.blogapp.dao.PostRepository;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Post> findPostsSortBy(String sortBy, String order) {
        Sort sort = null;

        if(order.equalsIgnoreCase("asc")  ||  order == null) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        return postRepository.findAll(sort);
    }

    @Override
    public List<Post> findPostsWithKeyword(String keyword) {
        return postRepository.findPostsWithKeyword(keyword);
    }

    @Override
    public List<Post> findPostsSortByWithKeyword(String sortBy, String order, String keyword) {
        Sort sort = null;

        if(order.equalsIgnoreCase("asc")  ||  order == null) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }

        return postRepository.findPostsSortByWithKeyword(keyword, sort);
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
