package com.clearsolutions.practicalTest.controller;


import com.clearsolutions.practicalTest.exception.ResourceNotFoundException;
import com.clearsolutions.practicalTest.model.User;
import com.clearsolutions.practicalTest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Controller class responsible for handling user-related HTTP requests.
     */

    /**
     * Retrieves all users.
     * @return ResponseEntity containing a list of users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by ID.
     * @param id the ID of the user to retrieve
     * @return ResponseEntity containing the user
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        }
    }

    /**
     * Creates a new user.
     * @param user the user to create
     * @return ResponseEntity containing the created user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     * @param id the ID of the user to update
     * @param user the updated user information
     * @return ResponseEntity containing the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        }
    }

    /**
     * Deletes a user by ID.
     * @param id the ID of the user to delete
     * @return ResponseEntity indicating success or failure of deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        }
    }

    /**
     * This method handles GET requests to search users by a specified birth date range.
     * It expects two date parameters: 'from' and 'to', which define the range of birth dates.
     *
     * @param from the start date of the birthdate range (inclusive).
     * @param to the end date of the birthdate range (inclusive).
     * @return a ResponseEntity containing a list of users whose birth dates fall within the specified range.
     * @throws ResponseStatusException if the 'from' date is after the 'to' date, indicating a bad request.
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> getUsersByBirthDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        try {
            List<User> users = userService.findUsersByBirthDateRange(from, to);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

}
