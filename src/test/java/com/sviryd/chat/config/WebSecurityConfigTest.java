package com.sviryd.chat.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WebSecurityConfigTest {
    @Autowired
    private MockMvc mvc;
    @Test
    @WithAnonymousUser
    public void whenUserAccessAdminSecuredEndpoint_thenIsUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/messages")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAnonymousAccessInit_thenOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/init")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void whenAnonymousAccessAuth_thenOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/auth")
                        .accept(MediaType.APPLICATION_JSON)
                        .queryParam("name", "Pavel")
                        .queryParam("sex", "M")
                )
                .andExpect(status().isOk());
    }
}