package com.sviryd.chat.service;


import com.sviryd.chat.domain.User;
import com.sviryd.chat.repo.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAll(Sort sort) {
        return userRepo.findAll(sort);
    }
}
