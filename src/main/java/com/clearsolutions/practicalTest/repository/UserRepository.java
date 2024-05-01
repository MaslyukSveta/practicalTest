package com.clearsolutions.practicalTest.repository;

import com.clearsolutions.practicalTest.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Repository class for managing users.
 */
@Component
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    /**
     * Saves or updates a user.
     * @param user the user to save or update
     * @return the saved or updated user
     */
    public User save(User user) {
        long id = Optional.ofNullable(user.getId()).orElseGet(counter::incrementAndGet);
        user.setId(id);
        users.put(id, user);
        logger.info("User saved or updated: {}", user);
        return user;
    }

    /**
     * Finds a user by ID.
     * @param id the ID of the user to find
     * @return an Optional containing the user if found, empty otherwise
     */
    public Optional<User> findById(Long id) {
        User user = users.get(id);
        if (user == null) {
            logger.info("No user found with ID: {}", id);
        } else {
            logger.info("Found user with ID: {}", id);
        }
        return Optional.ofNullable(user);
    }

    /**
     * Retrieves all users.
     * @return a list of all users
     */
    public List<User> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(users.values()));
    }

    /**
     * Deletes a user by ID.
     * @param id the ID of the user to delete
     * @return true if the user was deleted successfully, false otherwise
     */
    public boolean deleteById(Long id) {
        User removedUser = users.remove(id);
        if (removedUser == null) {
            logger.warn("Attempted to delete non-existent user with ID: {}", id);
            return false;
        } else {
            logger.info("Deleted user with ID: {}", id);
            return true;
        }
    }
}
