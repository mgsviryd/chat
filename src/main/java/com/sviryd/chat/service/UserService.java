package com.sviryd.chat.service;


import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.type.Male;
import com.sviryd.chat.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User save(User user) {
        return userRepo.save(user);
    }

    public void save(User user, String username, Male male) {
        user.setUsername(username);
        user.setMale(male);
        userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
