package com.clearsolutions.practicalTest;

import com.clearsolutions.practicalTest.exception.ResourceNotFoundException;
import com.clearsolutions.practicalTest.model.User;
import com.clearsolutions.practicalTest.repository.UserRepository;
import com.clearsolutions.practicalTest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@SpringBootTest
class PracticalTestApplicationTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void findAllUsers_ReturnsAllUsers() {
		when(userRepository.findAll()).thenReturn(Arrays.asList(
				new User(1L, "email1@example.com", "First", "User", LocalDate.of(2000, 1, 1), "Address", "Phone"),
				new User(2L, "email2@example.com", "Second", "User", LocalDate.of(1990, 1, 1), "Address", "Phone")
		));

		List<User> users = userService.findAllUsers();
		assertNotNull(users);
		assertEquals(2, users.size());
		assertEquals("First", users.get(0).getFirstName());
	}

	@Test
	public void findUserById_UserExists_ReturnsUser() {
		User user = new User(1L, "email@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "Address", "Phone");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		User foundUser = userService.findUserById(1L);
		assertNotNull(foundUser);
		assertEquals("John", foundUser.getFirstName());
	}

	@Test
	public void findUserById_UserDoesNotExist_ThrowsException() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			userService.findUserById(1L);
		});

		assertEquals("User not found with ID: 1", exception.getMessage());
	}

	@Test
	public void saveUser_ValidUser_SavesUser() {
		User user = new User(null, "newuser@example.com", "New", "User", LocalDate.of(2002, 1, 1), "123 Main St", "1234567890");
		when(userRepository.save(any(User.class))).thenReturn(user);

		User savedUser = userService.saveUser(user);
		assertNotNull(savedUser);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	public void updateUser_UserExists_UpdatesUser() {
		User existingUser = new User(1L, "email@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "Old Address", "Old Phone");
		User updatedDetails = new User(1L, "email@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "New Address", "New Phone");
		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(existingUser)).thenReturn(updatedDetails);

		User updatedUser = userService.updateUser(1L, updatedDetails);
		assertNotNull(updatedUser);
		assertEquals("New Address", updatedUser.getAddress());
		assertEquals("New Phone", updatedUser.getPhoneNumber());
	}



	@Test
	public void findUsersByBirthDateRange_ValidRange_ReturnsUsers() {
		LocalDate from = LocalDate.of(1980, 1, 1);
		LocalDate to = LocalDate.now();
		when(userRepository.findAll()).thenReturn(Arrays.asList(
				new User(1L, "email@example.com", "John", "Doe", LocalDate.of(1985, 5, 5), "123 Main St", "1234567890")
		));

		List<User> users = userService.findUsersByBirthDateRange(from, to);
		assertNotNull(users);
		assertFalse(users.isEmpty());
	}

	@Test
	public void findUsersByBirthDateRange_InvalidRange_ThrowsException() {
		LocalDate from = LocalDate.now();
		LocalDate to = LocalDate.of(1980, 1, 1);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.findUsersByBirthDateRange(from, to);
		});

		assertEquals("Start date must be before end date.", exception.getMessage());
	}
}
