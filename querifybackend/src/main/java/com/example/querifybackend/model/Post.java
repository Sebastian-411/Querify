package com.example.querifybackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a post made by a user, including its title, content, likes, and comments.
 */
@Entity
@Table(name = "posts")
public class Post {

    /**
     * The unique identifier for the post.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the post.
     */
    @Column(name = "title")
    private String title;

    /**
     * The content of the post.
     */
    @Column(name = "content", length = 600, columnDefinition = "VARCHAR(600)")
    private String content;


    @Column(name = "autorComment", length = 600, columnDefinition = "VARCHAR(600)")
    private String autorComment;

    /**
     * The user who created the post.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The number of likes the post has received.
     */
    @Column(name = "likes")
    private Integer likes;

    /**
     * The set of users who liked the post.
     * Using {@code @JsonBackReference} to break the potential infinite loop in JSON serialization.
     */
    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likedByUsers;

    /**
     * The list of comments associated with the post.
     * Using {@code @JsonManagedReference} to break the potential infinite loop in JSON serialization.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;


    /**
     * The list of queries associated with the post.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "query_id")
    private Query query;



    /**
     * Default constructor for the Post class.
     * Initializes the 'likes' count to 0.
     */
    public Post() {
        this.likes = 0;
    }

    /**
     * Parameterized constructor for the Post class.
     *
     * @param title   The title of the post.
     * @param content The content of the post.
     * @param user    The user who created the post.
     */
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.likes = 0;
    }

    /**
     * Gets the unique identifier of the post.
     *
     * @return The post's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the post.
     *
     * @param id The post's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of the post.
     *
     * @return The title of the post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the post.
     *
     * @param title The title of the post.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the content of the post.
     *
     * @return The content of the post.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     *
     * @param content The content of the post.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the user who created the post.
     *
     * @return The user who created the post.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who created the post.
     *
     * @param user The user who created the post.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the number of likes the post has received.
     *
     * @return The number of likes.
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * Sets the number of likes the post has received.
     *
     * @param likes The number of likes.
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * Gets the set of users who liked the post.
     *
     * @return The set of users who liked the post.
     */
    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    /**
     * Sets the set of users who liked the post.
     *
     * @param likedByUsers The set of users who liked the post.
     */
    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    /**
     * Adds a user to the set of users who liked the post and increments the 'likes' count.
     *
     * @param user The user who liked the post.
     */
    public void addLikedByUser(User user) {
        if (this.likes == null) {
            this.likes = 0;
        }
        this.likes = likes + 1;
        this.likedByUsers.add(user);
    }

    /**
     * Gets the list of comments associated with the post.
     *
     * @return The list of comments.
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets the list of comments associated with the post.
     *
     * @param comments The list of comments.
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getAutorComment() {
        return autorComment;
    }

    public void setAutorComment(String autorComment) {
        this.autorComment = autorComment;
    }
}

