package com.example.querifybackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String user;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers")
    private Set<Post> likedPosts;


    public User(long l, String user2) {
        this.id = l;
        this.user = user2;
    }

    public User() {
    }

    public User(Long userId) {
        this.id = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public void addLikedPost(Post post) {
        this.likedPosts.add(post);
    }
}