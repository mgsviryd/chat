package com.sviryd.chat.repo;

import com.sviryd.chat.domain.User;
import com.sviryd.chat.domain.resource.UserResource;
import com.sviryd.chat.domain.type.Gender;
import com.sviryd.chat.domain.type.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepoTest {
    private static final UserResource USER_CREDENTIAL = new UserResource("admin", "password");
    private User user;
    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    public void init() {
        user = userRepo.save(getUser());
    }
    @AfterEach
    public void deInit() {
        userRepo.delete(user);
    }

    @Test
    public void testSaveUser() {
        assertNotNull(user);
    }

    @Test
    public void testFindById() {
        Optional<User> optUser = userRepo.findById(user.getId());
        assertTrue(optUser.isPresent());
    }

    @Test
    public void testFindByUsername() {
        user = userRepo.findByUsername(user.getUsername());
        assertNotNull(user);
    }

    @Test
    public void testDeleteUser() {
        userRepo.delete(user);
        Optional<User> optUser = userRepo.findById(user.getId());
        assertFalse(optUser.isPresent());
    }

    private User getUser() {
        User user = new User();
        user.setUsername(USER_CREDENTIAL.getUsername());
        user.setGender(Gender.M);
        user.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));
        return user;
    }
}
