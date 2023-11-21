package com.example.querifybackend.controller;

import com.example.querifybackend.model.Query;
import com.example.querifybackend.model.User;
import com.example.querifybackend.repository.QueryRepository;
import com.example.querifybackend.repository.UserRepository;
import com.google.cloud.bigquery.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller class for handling queries.
 */
@RestController
@RequestMapping("/api/queries")
public class QueryController {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint to create a new query.
     *
     * @param query The query to be created.
     * @return The created query.
     */
    @PostMapping
    public ResponseEntity<Query> createQuery(@RequestBody Query query) {
        if (query.isEmpty() || query.getUser() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findById(query.getUser().getId()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        query.setUser(user);
        user.addQuery(query);
        Query createdQuery = queryRepository.save(query);
        return new ResponseEntity<>(createdQuery, HttpStatus.CREATED);
    }

    /**
     * Endpoint to delete a query by its ID.
     *
     * @param queryId The ID of the query to be deleted.
     * @return ResponseEntity with HTTP status indicating success or failure.
     */
    @DeleteMapping("/{queryId}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long queryId) {
        if (queryRepository.existsById(queryId)) {
            queryRepository.deleteById(queryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to update an existing query.
     *
     * @param queryId      The ID of the query to be updated.
     * @param updatedQuery The updated query.
     * @return The updated query.
     */
    @PutMapping("/{queryId}")
    public ResponseEntity<Query> updateQuery(@PathVariable Long queryId, @RequestBody Query updatedQuery) {
        if (queryRepository.existsById(queryId)) {
            updatedQuery.setId(queryId);
            Query result = queryRepository.save(updatedQuery);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to retrieve a query by its ID.
     *
     * @param queryId The ID of the query to be retrieved.
     * @return The retrieved query.
     */
    @GetMapping("/{queryId}")
    public ResponseEntity<Query> getQueryById(@PathVariable Long queryId) {
        return queryRepository.findById(queryId)
                .map(query -> new ResponseEntity<>(query, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to retrieve all queries.
     *
     * @return The list of all queries.
     */
    @GetMapping
    public ResponseEntity<List<Query>> getAllQueries() {
        List<Query> queries = queryRepository.findAll();
        return new ResponseEntity<>(queries, HttpStatus.OK);
    }

    @GetMapping("/execute/{queryId}")
    public ResponseEntity<List<Map<String, Object>>> executeQuery(@PathVariable Long queryId) {
        try {

            Query query = queryRepository.findById(queryId).orElse(null);
            if (query == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(query.execute(), HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/execute")
    public ResponseEntity<List<Map<String, Object>>> executeQuery(@RequestParam String queryString) {
        Query query = new Query();
        query.setContent(queryString);
        try {
            return new ResponseEntity<>(query.execute(), HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}