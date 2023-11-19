package com.example.querifybackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a user in the system, including their username and liked posts.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    @Column(name = "username")
    private String user;

    /**
     * The set of posts liked by the user.
     * Using {@code @JsonIgnore} to prevent infinite loops in JSON serialization.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers")
    private Set<Post> likedPosts;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Query> queries;



    /**
     * Parameterized constructor for the User class.
     *
     * @param id   The unique identifier for the user.
     * @param user The username of the user.
     */
    public User(long id, String user) {
        this.id = id;
        this.user = user;
    }

    /**
     * Default constructor for the User class.
     */
    public User() {
    }

    /**
     * Constructor for creating a user with a specified ID.
     *
     * @param userId The unique identifier for the user.
     */
    public User(Long userId) {
        this.id = userId;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id The user's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the username of the user.
     *
     * @param user The username of the user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the set of posts liked by the user.
     *
     * @return The set of liked posts.
     */
    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    /**
     * Sets the set of posts liked by the user.
     *
     * @param likedPosts The set of liked posts.
     */
    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    /**
     * Adds a post to the set of liked posts by the user.
     *
     * @param post The post to be added to liked posts.
     */
    public void addLikedPost(Post post) {
        this.likedPosts.add(post);
    }


    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }
}
