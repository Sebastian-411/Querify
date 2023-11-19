package com.example.querifybackend.controller;

import com.example.querifybackend.model.Query;
import com.example.querifybackend.repository.QueryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class QueryControllerTest {
    private MockMvc mockMvc;

    @Mock
    private QueryRepository queryRepository;

    @InjectMocks
    private QueryController queryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(queryController).build();
    }

    /**
     * Test case to verify that creating an invalid query (empty JSON) returns a Bad Request status.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void createQuery_InvalidQuery_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/queries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case to verify that deleting an existing query returns a No Content status.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void deleteQuery_ExistingQuery_ReturnsNoContent() throws Exception {
        Long queryId = 1L;
        when(queryRepository.existsById(queryId)).thenReturn(true);

        mockMvc.perform(delete("/api/queries/{queryId}", queryId))
                .andExpect(status().isNoContent());
    }

    /**
     * Test case to verify that deleting a non-existing query returns a Not Found status.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void deleteQuery_NonExistingQuery_ReturnsNotFound() throws Exception {
        Long queryId = 1L;
        when(queryRepository.existsById(queryId)).thenReturn(false);

        mockMvc.perform(delete("/api/queries/{queryId}", queryId))
                .andExpect(status().isNotFound());
    }

    /**
     * Test case to verify that updating an existing query returns an Ok status.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void updateQuery_ExistingQuery_ReturnsOk() throws Exception {
        Long queryId = 1L;
        Query updatedQuery = new Query();
        updatedQuery.setId(queryId);
        when(queryRepository.existsById(queryId)).thenReturn(true);
        when(queryRepository.save(any(Query.class))).thenReturn(updatedQuery);

        mockMvc.perform(put("/api/queries/{queryId}", queryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedQuery)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(queryId));
    }

    /**
     * Test case to verify that updating a non-existing query returns a Not Found status.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void updateQuery_NonExistingQuery_ReturnsNotFound() throws Exception {
        Long queryId = 1L;
        when(queryRepository.existsById(queryId)).thenReturn(false);

        mockMvc.perform(put("/api/queries/{queryId}", queryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Send an invalid JSON
                .andExpect(status().isNotFound());
    }

    /**
     * Test case to verify that retrieving an existing query by ID returns an Ok status.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getQueryById_ExistingQuery_ReturnsOk() throws Exception {
        Long queryId = 1L;
        Query query = new Query();
        query.setId(queryId);
        when(queryRepository.findById(queryId)).thenReturn(Optional.of(query));

        mockMvc.perform(get("/api/queries/{queryId}", queryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(queryId));
    }

    /**
     * Test case to verify that retrieving a non-existing query by ID returns a Not Found status.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getQueryById_NonExistingQuery_ReturnsNotFound() throws Exception {
        Long queryId = 1L;
        when(queryRepository.findById(queryId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/queries/{queryId}", queryId))
                .andExpect(status().isNotFound());
    }

    /**
     * Test case to verify that retrieving all queries when there are none returns an empty list.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    void getAllQueries_NoQueries_ReturnsEmptyList() throws Exception {
        when(queryRepository.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }


}
