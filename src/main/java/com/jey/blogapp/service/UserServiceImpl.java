package com.jey.blogapp.service;

import com.jey.blogapp.dao.UserRepository;
import com.jey.blogapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(int id) {
        User user = null;
        Optional<User> result = userRepository.findById(id);

        if(result.isPresent()) {
            user = result.get();
        }

        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}
