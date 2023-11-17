package com.example.querifybackend.controller;

import com.example.querifybackend.model.Post;
import com.example.querifybackend.model.User;
import com.example.querifybackend.repository.PostRepository;
import com.example.querifybackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getPosts_ReturnsListOfPosts() {
        // Arrange
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postRepository.findAll()).thenReturn(posts);

        // Act
        ResponseEntity<List<Post>> responseEntity = postController.getPosts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(posts, responseEntity.getBody());
    }

    @Test
    void savePost_WithValidUser_ReturnsCreated() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Post post = new Post();
        post.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Act
        ResponseEntity<Post> responseEntity = postController.savePost(post, 1L);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(post, responseEntity.getBody());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void savePost_WithInvalidUser_ReturnsBadRequest() {
        // Arrange
        Post post = new Post();
        post.setUser(new User());

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Post> responseEntity = postController.savePost(post, 1L);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void getPostById_WithValidId_ReturnsPost() {
        // Arrange
        Long postId = 1L;
        Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        ResponseEntity<Post> responseEntity = postController.getPostById(postId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(post, responseEntity.getBody());
    }

    @Test
    void getPostById_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Post> responseEntity = postController.getPostById(postId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void updatePost_WithValidIdAndData_ReturnsUpdatedPost() {
        // Arrange
        Long postId = 1L;
        Post existingPost = new Post();
        existingPost.setId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");

        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        // Act
        ResponseEntity<Post> responseEntity = postController.updatePost(postId, updatedPost);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedPost, responseEntity.getBody());
    }

    @Test
    void updatePost_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Post updatedPost = new Post();
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");

        // Act
        ResponseEntity<Post> responseEntity = postController.updatePost(postId, updatedPost);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deletePost_WithValidId_ReturnsNoContent() {
        // Arrange
        Long postId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = postController.deletePost(postId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    void likePost_WithValidData_ReturnsOk() {
        // Arrange
        Long postId = 1L;
        Long userId = 2L;

        Post post = new Post();
        post.setId(postId);

        User user = new User();
        user.setId(userId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<Void> responseEntity = postController.likePost(postId, user.getId());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void likePost_PostNotFound_ReturnsNotFound() {
        // Arrange
        Long postId = 1L;
        Long userId = 2L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> responseEntity = postController.likePost(postId, userId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(postRepository, never()).save(any());
    }



}
