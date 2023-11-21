package com.example.querifybackend.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommentTest {

    private Comment comment;
    private User mockUser;
    private Post mockPost;

    @Before
    public void setUp() {
        mockUser = mock(User.class);
        mockPost = mock(Post.class);
        comment = new Comment();
    }

    @Test
    public void testGetId() {
        assertNull(comment.getId());
    }

    @Test
    public void testSetId() {
        comment.setId(1L);
        assertEquals(Long.valueOf(1L), comment.getId());
    }

    @Test
    public void testGetText() {
        assertNull(comment.getText());
    }

    @Test
    public void testSetText() {
        comment.setText("Test Comment");
        assertEquals("Test Comment", comment.getText());
    }

    @Test
    public void testGetUser() {
        assertNull(comment.getUser());
    }

    @Test
    public void testSetUser() {
        comment.setUser(mockUser);
        assertEquals(mockUser, comment.getUser());
    }

    @Test
    public void testGetPost() {
        assertNull(comment.getPost());
    }

    @Test
    public void testSetPost() {
        comment.setPost(mockPost);
        assertEquals(mockPost, comment.getPost());
    }
}
