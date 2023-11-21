package com.example.querifybackend.model;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QueryTest {

    private Query query;
    private User mockUser;

    @Before
    public void setUp() {
        mockUser = mock(User.class);
        query = new Query("Test Query", "SELECT * FROM SomeTable", mockUser);
    }

    @Test
    public void testGetId() {
        assertNull(query.getId());
    }

    @Test
    public void testSetId() {
        query.setId(1L);
        assertEquals(Long.valueOf(1L), query.getId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Test Query", query.getTitle());
    }

    @Test
    public void testSetTitle() {
        query.setTitle("New Query");
        assertEquals("New Query", query.getTitle());
    }

    @Test
    public void testGetContent() {
        assertEquals("SELECT * FROM SomeTable", query.getContent());
    }

    @Test
    public void testSetContent() {
        query.setContent("SELECT * FROM AnotherTable");
        assertEquals("SELECT * FROM AnotherTable", query.getContent());
    }

    @Test
    public void testGetUser() {
        assertEquals(mockUser, query.getUser());
    }

    @Test
    public void testSetUser() {
        User newUser = mock(User.class);
        query.setUser(newUser);
        assertEquals(newUser, query.getUser());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(query.isEmpty());
        Query emptyQuery = new Query();
        assertTrue(emptyQuery.isEmpty());
    }
}
