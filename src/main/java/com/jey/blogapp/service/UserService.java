package com.jey.blogapp.service;

import com.jey.blogapp.entity.User;

import java.util.List;

public interface UserService {
    User findById(int id);
    User findByName(String name);
    List<User> findAll();
    void save(User user);
    void deleteById(int id);
}
