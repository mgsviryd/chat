package com.sviryd.chat.controller.rest;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.Views;
import com.sviryd.chat.domain.type.Male;
import com.sviryd.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserRestController {
    private static final String DEFAULT_PASSWORD = "123456";
    private static final Sort BY_CREATION_LDT_DESC = Sort.by("creationLDT").descending();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @GetMapping("/init")
    public HashMap<Object, Object> isAuthenticated(
            @AuthenticationPrincipal User user
    ) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("result", user != null);
        return data;
    }

    @PostMapping("/auth")
    public HashMap<Object, Object> saveAndLogin(
            @RequestParam(value = "name") String username,
            @RequestParam(value = "sex") Male male
            ) {
        User user = new User();
        user.setUsername(username);
        user.setMale(male);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        User userDB = userService.findByUsername(username);
        if (userDB == null) {
            userService.save(user);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, DEFAULT_PASSWORD);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HashMap<Object, Object> data = new HashMap<>();
        data.put("result", true);
        return data;
    }

    @GetMapping("/users")
    @JsonView({Views.Users.class})
    public HashMap<Object, Object> getUsers() {
        HashMap<Object, Object> data = new HashMap<>();
        List<User> users = userService.findAll(BY_CREATION_LDT_DESC);
        data.put("result", true);
        data.put("data", users);
        return data;
    }
}
