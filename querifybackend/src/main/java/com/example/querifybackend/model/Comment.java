package com.example.querifybackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Represents a comment on a post made by a user.
 */
@Entity
public class Comment {
    /**
     * The unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The text content of the comment.
     */
    private String text;

    /**
     * The user who made the comment.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The post to which the comment belongs.
     * Using {@code @JsonBackReference} to break the potential infinite loop in JSON serialization.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * Gets the unique identifier of the comment.
     *
     * @return The comment's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the comment.
     *
     * @param id The comment's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the text content of the comment.
     *
     * @return The text content of the comment.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text content of the comment.
     *
     * @param text The text content of the comment.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the user who made the comment.
     *
     * @return The user who made the comment.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made the comment.
     *
     * @param user The user who made the comment.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the post to which the comment belongs.
     *
     * @return The post to which the comment belongs.
     */
    public Post getPost() {
        return post;
    }

    /**
     * Sets the post to which the comment belongs.
     *
     * @param post The post to which the comment belongs.
     */
    public void setPost(Post post) {
        this.post = post;
    }
}
