package com.sviryd.chat.service;

import com.sviryd.chat.domain.User;
import com.sviryd.chat.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {UserService.class})
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepo userRepo;
    @Test
    public void testFindUserById() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("Pavel");
        user.setPassword("password");
        // Mock the behavior of the repository to return the mock employee
        Mockito.when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        // Act
        Optional<User> result = userService.findById(user.getId());

        // Assert
        assertNotNull(result.orElse(null));
        assertEquals(user.getId(), result.get().getId());
    }
    @Test
    public void testFindUserByUsername() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("Pavel");
        user.setPassword("password");
        // Mock the behavior of the repository to return the mock employee
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        // Act
        User result = userService.findByUsername(user.getUsername());

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }
}
