package com.jey.blogapp.service;

import com.jey.blogapp.dao.PostTagRepository;
import com.jey.blogapp.entity.PostTag;
import com.jey.blogapp.entity.PostTagId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostTagServiceImpl implements PostTagService {
    PostTagRepository postTagRepository;

    @Autowired
    public PostTagServiceImpl(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }

    @Override
    public void save(PostTag postTag) {
        postTagRepository.save(postTag);
    }

    @Override
    public List<PostTag> findAll() {
        return postTagRepository.findAll();
    }

    @Override
    public PostTag findById(PostTagId id) {
        Optional<PostTag> result = postTagRepository.findById(id);
        PostTag postTag = null;

        if(result.isPresent()) {
            postTag = result.get();
        }

        return postTag;
    }

    @Override
    public void deleteById(PostTagId id) {
        postTagRepository.deleteById(id);
    }
}
