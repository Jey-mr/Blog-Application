package com.jey.blogapp.service;

import com.jey.blogapp.dao.TagRepository;
import com.jey.blogapp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(int id) {
        Optional<Tag> result = tagRepository.findById(id);
        Tag tag = null;

        if (result.isPresent()) {
            tag = result.get();
        }

        return tag;
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public void deleteById(int id) {
        tagRepository.deleteById(id);
    }
}
