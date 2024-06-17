package com.jey.blogapp.dao;

import com.jey.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("select p from Post p " +
            "where p.title like %:keyword% " +
            "or p.content like %:keyword% " +
            "or p.user.name like %:keyword% " +
            "union " +
            "select p from Post p " +
            "join PostTag pt on p.id = pt.id.postId " +
            "join Tag t on t.id = pt.id.tagId " +
            "where t.name like %:keyword%")
    List<Post> findPostsWithKeyword(String keyword);
}
