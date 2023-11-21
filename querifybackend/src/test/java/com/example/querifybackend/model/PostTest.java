package com.example.querifybackend.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostTest {

    private Post post;
    private User mockUser;

    @Before
    public void setUp() {
        mockUser = mock(User.class);
        post = new Post("Test Post", "Post Content", mockUser);
    }

    @Test
    public void testGetId() {
        assertNull(post.getId());
    }

    @Test
    public void testSetId() {
        post.setId(1L);
        assertEquals(Long.valueOf(1L), post.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Test Post", post.getTitle());
    }

    @Test
    public void testSetTitle() {
        post.setTitle("New Post");
        assertEquals("New Post", post.getTitle());
    }

    @Test
    public void testGetContent() {
        assertEquals("Post Content", post.getContent());
    }

    @Test
    public void testSetContent() {
        post.setContent("New Content");
        assertEquals("New Content", post.getContent());
    }

    @Test
    public void testGetUser() {
        assertEquals(mockUser, post.getUser());
    }

    @Test
    public void testSetUser() {
        User newUser = mock(User.class);
        post.setUser(newUser);
        assertEquals(newUser, post.getUser());
    }


    @Test
    public void testSetLikes() {
        post.setLikes(10);
        assertEquals(Integer.valueOf(10), post.getLikes());
    }

    @Test
    public void testSetLikedByUsers() {
        Set<User> likedByUsers = new HashSet<>();
        likedByUsers.add(mockUser);
        post.setLikedByUsers(likedByUsers);
        assertEquals(likedByUsers, post.getLikedByUsers());
    }




    @Test
    public void testSetComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(mock(Comment.class));
        post.setComments(comments);
        assertEquals(comments, post.getComments());
    }

    @Test
    public void testGetQuery() {
        assertNull(post.getQuery());
    }

    @Test
    public void testSetQuery() {
        Query mockQuery = mock(Query.class);
        post.setQuery(mockQuery);
        assertEquals(mockQuery, post.getQuery());
    }

    @Test
    public void testGetAutorComment() {
        assertNull(post.getAutorComment());
    }

    @Test
    public void testSetAutorComment() {
        post.setAutorComment("Author's Comment");
        assertEquals("Author's Comment", post.getAutorComment());
    }
}
