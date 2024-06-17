package com.jey.blogapp.dao;

import com.jey.blogapp.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
/*
    @Query("select p from Post p " +
            "where p.title like %:keyword% " +
            "or p.content like %:keyword% " +
            "or p.user.name like %:keyword% " +
            "or exists (select 1 from PostTag pt join Tag t on t.id = pt.id.tagId " +
            "where pt.id.postId = p.id and t.name like %:keyword%)")
    List<Post> findPostsWithKeyword(String keyword);
*/


/*
    @Query("select p from Post p " +
            "where p.title like %:keyword% " +
            "or p.content like %:keyword% " +
            "or p.user.name like %:keyword% " +
            "or exists (select 1 from PostTag pt join Tag t on t.id = pt.id.tagId " +
            "where pt.id.postId = p.id and t.name like %:keyword%)")
    List<Post> findPostsSortByWithKeyword(String keyword, Sort sort);
*/


    @Query("select p from Post p " +
            "where p.title like %:keyword% " +
            "or p.content like %:keyword% " +
            "or p.user.name like %:keyword% " +
            "or exists (select 1 from PostTag pt join Tag t on t.id = pt.id.tagId " +
            "where pt.id.postId = p.id and t.name like %:keyword%)")
    List<Post> findPostsWithKeyword(String keyword, Pageable pageable);

    @Query("select p from Post p " +
            "where p.title like %:keyword% " +
            "or p.content like %:keyword% " +
            "or p.user.name like %:keyword% " +
            "or exists (select 1 from PostTag pt join Tag t on t.id = pt.id.tagId " +
            "where pt.id.postId = p.id and t.name like %:keyword%)")
    List<Post> findPostsSortByWithKeyword(String keyword, Pageable pageable);

}
