package com.sviryd.chat.controller;

import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.resource.UserResource;
import com.sviryd.chat.domain.type.Gender;
import com.sviryd.chat.domain.type.Role;
import com.sviryd.chat.service.AuthenticationService;
import com.sviryd.chat.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MessageControllerTest {
    private static final String TEXT = "Hello world";
    private static final UserResource USER_CREDENTIAL = new UserResource("admin", "password");
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        this.saveAndAuthenticateUser();
    }

    @AfterEach
    public void destroy() {
        this.deleteUser();
    }

    @Test
    public void postMessages() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/messages").with(user(USER_CREDENTIAL.getUsername()).password(USER_CREDENTIAL.getPassword()).roles(Role.ADMIN.toString()))
                        .content(TEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(AuthenticationService.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(TEXT));
    }

    private void saveAndAuthenticateUser() {
        this.saveUser();
        this.authenticateUser();
    }

    private void saveUser() {
        User user = getUser();
        userService.save(user);
    }

    private User getUser() {
        User user = new User();
        user.setUsername(USER_CREDENTIAL.getUsername());
        user.setPassword(passwordEncoder.encode(USER_CREDENTIAL.getPassword()));
        user.setGender(Gender.M);
        user.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));
        return user;
    }

    private void deleteUser() {
        userService.delete(getUser());
    }

    private void authenticateUser() {
        authenticationService.authenticate(USER_CREDENTIAL);
    }
}
