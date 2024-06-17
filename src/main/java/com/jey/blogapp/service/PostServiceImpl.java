package com.jey.blogapp.service;

import com.jey.blogapp.dao.PostRepository;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Post> findPostsWithKeyword(String keyword) {
        return postRepository.findPostsWithKeyword(keyword);
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
