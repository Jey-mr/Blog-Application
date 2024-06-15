package com.jey.blogapp.service;

import com.jey.blogapp.entity.Tag;

import java.util.List;

public interface TagService {
    void save(Tag tag);
    List<Tag> findAll();
    Tag findById(int id);
    Tag findByName(String name);
    void deleteById(int id);
}
