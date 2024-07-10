package com.sviryd.chat.controller.rest;

import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.resource.UserResource;
import com.sviryd.chat.domain.type.Gender;
import com.sviryd.chat.dto.UserDTO;
import com.sviryd.chat.dto.UsersDTO;
import com.sviryd.chat.service.AuthenticationService;
import com.sviryd.chat.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserRestController(final PasswordEncoder passwordEncoder, final UserService userService, final AuthenticationService authenticationService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/init")
    public HashMap<Object, Object> isAuthenticated(
            @AuthenticationPrincipal User user
    ) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("result", user != null);
        return data;
    }

    @PostMapping("/auth")
    public HashMap<Object, Object> saveAndAuthenticateUser(
            @RequestParam(value = "name") String username,
            @RequestParam(value = "sex") Gender gender,
            HttpSession session
    ) {
        User user = new User();
        user.setUsername(username);
        user.setGender(gender);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        userService.saveIfNotExists(user);
        UserResource credentials = new UserResource(username, DEFAULT_PASSWORD);
        authenticationService.authenticate(credentials, session);
        HashMap<Object, Object> data = new HashMap<>();
        data.put("result", true);
        return data;
    }

    @GetMapping("/users")
    public UsersDTO getUsers() {
        List<User> users = userService.findAll(BY_CREATION_LDT_DESC);
        List<UserDTO> dtos = users.stream().map(UserDTO::toDTO).toList();
        return UsersDTO.builder()
                .result(true)
                .data(dtos)
                .build();
    }
}
