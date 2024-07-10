package com.sviryd.chat.service;


import com.sviryd.chat.domain.User;
import com.sviryd.chat.repo.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User save(User user) {
        return userRepo.save(user);
    }
    public User saveIfNotExists(User user){
        User userDB = findByUsername(user.getUsername());
        if (userDB == null) {
            userDB = save(user);
        }
        return userDB;
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAll(Sort sort) {
        return userRepo.findAll(sort);
    }
}
