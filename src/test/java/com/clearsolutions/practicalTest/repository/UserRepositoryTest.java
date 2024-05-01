package com.clearsolutions.practicalTest.repository;

import com.clearsolutions.practicalTest.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void testSaveNewUser() {
        User newUser = new User(null, "john@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-1234");
        User savedUser = userRepository.save(newUser);

        assertNotNull(savedUser.getId());
        assertEquals("John", savedUser.getFirstName());
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    public void testSaveUpdateUser() {
        User user = new User(null, "john@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-1234");
        User savedUser = userRepository.save(user);
        savedUser.setEmail("john_updated@example.com");
        User updatedUser = userRepository.save(savedUser);

        assertEquals("john_updated@example.com", updatedUser.getEmail());
        assertEquals(1, userRepository.findAll().size()); // Ensure no new records are created
    }

    @Test
    public void testFindByIdExistingUser() {
        User user = new User(null, "jane@example.com", "Jane", "Doe", LocalDate.of(1995, 5, 15), "456 Elm St", "555-6789");
        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("Jane", foundUser.get().getFirstName());
    }

    @Test
    public void testFindByIdNonExistingUser() {
        Optional<User> foundUser = userRepository.findById(999L);
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindAllUsers() {
        userRepository.save(new User(null, "jane@example.com", "Jane", "Doe", LocalDate.of(1995, 5, 15), "456 Elm St", "555-6789"));
        userRepository.save(new User(null, "john@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-1234"));

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void testDeleteByIdExistingUser() {
        User user = new User(null, "jane@example.com", "Jane", "Doe", LocalDate.of(1995, 5, 15), "456 Elm St", "555-6789");
        User savedUser = userRepository.save(user);

        boolean result = userRepository.deleteById(savedUser.getId());
        assertTrue(result);
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    public void testDeleteByIdNonExistingUser() {
        boolean result = userRepository.deleteById(999L);
        assertFalse(result);
    }
}