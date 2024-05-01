package com.clearsolutions.practicalTest.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.clearsolutions.practicalTest.model.User;
import com.clearsolutions.practicalTest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testSaveUser_ValidAge() {

        User user = new User(null, "validuser@example.com", "Valid", "User", LocalDate.of(1990, 1, 1), null, null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Valid", savedUser.getFirstName());
    }

    @Test
    public void testFindUsersByBirthDateRange_ValidRange() {
        LocalDate from = LocalDate.of(1980, 1, 1);
        LocalDate to = LocalDate.now();
        when(userRepository.findAll()).thenReturn(Arrays.asList(
                new User(1L, "user@example.com", "John", "Doe", LocalDate.of(1985, 5, 5), "123 Main St", "555-1234")
        ));

        List<User> users = userService.findUsersByBirthDateRange(from, to);

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getFirstName());
    }
}