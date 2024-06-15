package com.jey.blogapp.dao;

import com.jey.blogapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("select t from Tag t where t.name = ?1")
    Tag findByName(String name);
}
