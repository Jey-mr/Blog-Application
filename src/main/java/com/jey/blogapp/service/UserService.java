package com.jey.blogapp.service;

import com.jey.blogapp.entity.User;

public interface UserService {
    User findById(int id);
    void save(User user);
}
