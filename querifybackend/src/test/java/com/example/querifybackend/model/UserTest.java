package com.example.querifybackend.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User(1L, "testUser");
        user.setLikedPosts(new HashSet<>());
        user.setQueries(new ArrayList<>());
    }

    @Test
    public void testGetId() {
        assertEquals(Long.valueOf(1L), user.getId());
    }

    @Test
    public void testSetId() {
        user.setId(2L);
        assertEquals(Long.valueOf(2L), user.getId());
    }

    @Test
    public void testGetUser() {
        assertEquals("testUser", user.getUser());
    }

    @Test
    public void testSetUser() {
        user.setUser("newUser");
        assertEquals("newUser", user.getUser());
    }

    @Test
    public void testGetLikedPosts() {
        assertNotNull(user.getLikedPosts());
    }

    @Test
    public void testSetLikedPosts() {
        Set<Post> likedPosts = new HashSet<>();
        likedPosts.add(new Post("Test Post", "Test Content", new User()));
        user.setLikedPosts(likedPosts);
        assertEquals(likedPosts, user.getLikedPosts());
    }

    @Test
    public void testAddLikedPost() {
        Post post = new Post("Test Post", "Test Content", new User());
        user.addLikedPost(post);
        assertTrue(user.getLikedPosts().contains(post));
    }

    @Test
    public void testGetQueries() {
        assertNotNull(user.getQueries());
    }

    @Test
    public void testSetQueries() {
        List<Query> queries = new ArrayList<>();
        queries.add(new Query());
        user.setQueries(queries);
        assertEquals(queries, user.getQueries());
    }

    @Test
    public void testAddQuery() {
        Query query = new Query();
        user.addQuery(query);
        assertTrue(user.getQueries().contains(query));
    }
}
