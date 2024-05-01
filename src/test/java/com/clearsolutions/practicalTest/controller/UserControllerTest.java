package com.clearsolutions.practicalTest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import com.clearsolutions.practicalTest.model.User;
import com.clearsolutions.practicalTest.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;



@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {

        given(userService.findAllUsers()).willReturn(Arrays.asList(new User(1L, "user1@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-1234")));


        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }
    @Test
    public void getAllUsers_ShouldReturnAllUsers() throws Exception {
        List<User> users = Arrays.asList(new User(1L, "john@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-1234"));
        given(userService.findAllUsers()).willReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("john@example.com")));
    }

    @Test
    public void getUserById_ShouldReturnUser() throws Exception {
        User user = new User(1L, "john@example.com", "John", "Doe", LocalDate.of(1990, 1, 1), "123 Main St", "555-1234");
        given(userService.findUserById(1L)).willReturn(user);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }


    @Test
    public void deleteUser_ShouldReturnNoContent() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }



}