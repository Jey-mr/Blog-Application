package com.jey.blogapp.service;

import com.jey.blogapp.dao.CommentRepository;
import com.jey.blogapp.entity.Comment;
import com.jey.blogapp.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findByPostId(int postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment findById(int id) {
        Optional<Comment> result = commentRepository.findById(id);
        Comment comment = null;

        if(result.isPresent()) {
            comment = result.get();
        }

        return comment;
    }

    @Override
    public void deleteById(int id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
    }
}
