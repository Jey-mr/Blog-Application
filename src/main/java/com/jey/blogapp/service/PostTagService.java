package com.jey.blogapp.service;

import com.jey.blogapp.entity.PostTag;
import com.jey.blogapp.entity.PostTagId;

import java.util.List;

public interface PostTagService {
    void save(PostTag postTag);
    List<PostTag> findAll();
    PostTag findById(PostTagId postTagId);
    void deleteById(PostTagId postTagId);
}
