package com.example.querifybackend.model;

import com.example.querifybackend.config.BigQueryConnection;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQueryException;
import jakarta.persistence.*;
import com.google.cloud.bigquery.*;
import jakarta.persistence.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "queries")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 600, columnDefinition = "VARCHAR(600)")
    private String title;

    @Column(name = "content", length = 600, columnDefinition = "VARCHAR(600)")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Query() {
    }

    public Query(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // Getters y setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEmpty() {
        return id == null &&
                (title == null || title.trim().isEmpty()) &&
                (content == null || content.trim().isEmpty()) &&
                user == null;
    }

    public List<Map<String, Object>> execute() throws InterruptedException {
        BigQueryConnection bigquery = BigQueryConnection.getInstance();
        return bigquery.getData(getContent());
    }

}
