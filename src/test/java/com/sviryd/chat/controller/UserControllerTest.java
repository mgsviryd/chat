package com.sviryd.chat.controller;

import com.sviryd.chat.controller.rest.UserRestController;
import com.sviryd.chat.domain.type.Gender;
import com.sviryd.chat.service.AuthenticationService;
import com.sviryd.chat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveAndAuthenticateUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/auth")
                        .param("name", "Pavel")
                        .param("sex", Gender.M.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true));
    }
}
