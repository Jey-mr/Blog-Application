package com.jey.blogapp.dao;

import com.jey.blogapp.entity.PostTag;
import com.jey.blogapp.entity.PostTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {
}
