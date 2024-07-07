package com.sviryd.chat.controller;

import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.type.Gender;
import com.sviryd.chat.domain.type.Role;
import com.sviryd.chat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MessageControllerTest {
    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    private static final String TEXT = "Hello world";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void postMessages() throws Exception {
        this.saveAndAuthenticateUser();
        mvc.perform(MockMvcRequestBuilders
                        .post("/messages").with(user(USERNAME).password(PASSWORD).roles(Role.ADMIN.toString()))
                        .content(TEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(SPRING_SECURITY_CONTEXT, SecurityContextHolder.getContext())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(TEXT));
    }

    private void saveAndAuthenticateUser() {
        this.saveUser();
        this.authenticateUser();
    }

    private void saveUser() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setGender(Gender.M);
        user.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));
        userService.save(user);
    }

    private void authenticateUser() {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
