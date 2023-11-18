package com.example.querifybackend.controller;

import com.example.querifybackend.repository.UserRepository;
import com.example.querifybackend.model.User;
import com.example.querifybackend.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link UserController} class.
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    /**
     * Tests the retrieval of a list of users and verifies that the correct list is returned.
     */
    @Test
    void getUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<User> mockUsers = Arrays.asList(
                new User(1L, "user1"),
                new User(2L, "user2")
        );
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        ResponseEntity<List<User>> response = userController.getUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsers, response.getBody());
    }

    /**
     * Tests saving a user and verifies that the correct saved user is returned.
     */
    @Test
    void saveUser_ShouldReturnSavedUser() {
        // Arrange
        User newUser = new User(2L, "newUser");
        User savedUser = new User(1L, "newUser");
        when(userRepository.save(newUser)).thenReturn(savedUser);

        // Act
        ResponseEntity<User> response = userController.saveUser(newUser);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedUser, response.getBody());
    }

    /**
     * Tests the retrieval of a user by ID with an existing user ID and verifies that the correct user is returned.
     */
    @Test
    void getById_ExistingUser_ShouldReturnUser() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User(userId, "existingUser");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Act
        ResponseEntity<User> response = userController.getById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingUser, response.getBody());
    }

    /**
     * Tests the retrieval of a user by ID with a non-existing user ID and verifies that NotFound status is returned.
     */
    @Test
    void getById_NonExistingUser_ShouldReturnNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.getById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    /**
     * Tests the deletion of a user by ID and verifies that NoContent status is returned.
     */
    @Test
    void deleteUserById_ShouldReturnNoContent() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<Void> response = userController.deleteUserById(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userRepository, times(1)).deleteById(userId);
    }
}
