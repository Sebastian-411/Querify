package com.example.querifybackend.controller;

import com.example.querifybackend.model.Query;
import com.example.querifybackend.model.Comment;
import com.example.querifybackend.repository.CommentRepository;
import com.example.querifybackend.repository.PostRepository;
import com.example.querifybackend.repository.QueryRepository;
import com.example.querifybackend.repository.UserRepository;
import com.example.querifybackend.model.Post;
import com.example.querifybackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Controller class for handling HTTP requests related to posts, likes, and comments.
 */

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QueryRepository queryRepository;

    /**
     * Retrieves all posts.
     *
     * @return ResponseEntity with the list of posts and HTTP status.
     */
    @GetMapping()
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Saves a new post associated with the specified user.
     *
     * @param post   The post to be saved.
     * @param userId The ID of the user associated with the post.
     * @return ResponseEntity with the saved post and HTTP status.
     */
    @PostMapping("/{userId}/{queryId}")
    public ResponseEntity<Post> savePost(@PathVariable("userId") Long userId, @PathVariable("queryId") Long queryId, @RequestParam("autorComment") String autorComment) {
        Optional<User> user = userRepository.findById(userId);
        Post post = new Post();
        Optional<Query> query = queryRepository.findById(queryId);
        if (user.isPresent() && query.isPresent()) {
            post.setUser(user.get());
            post.setQuery(query.get());
            post.setAutorComment(autorComment);
            post.setTitle(query.get().getTitle());
            post.setContent(query.get().getContent());
            Post savedPost = postRepository.save(post);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post to be retrieved.
     * @return ResponseEntity with the retrieved post and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing post by its ID.
     *
     * @param id          The ID of the post to be updated.
     * @param updatedPost The updated post data.
     * @return ResponseEntity with the updated post and HTTP status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());

            if (updatedPost.getUser() != null) {
                Optional<User> user = userRepository.findById(updatedPost.getUser().getId());
                if (user.isPresent()) {
                    post.setUser(user.get());
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            Post updated = postRepository.save(post);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to be deleted.
     * @return ResponseEntity with HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        postRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Adds a like to a post by a user.
     *
     * @param postId The ID of the post to be liked.
     * @param userId The ID of the user giving the like.
     * @return ResponseEntity with HTTP status.
     */
    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<Void> likePost(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            boolean userAlreadyLiked = post.getLikedByUsers().stream()
                    .anyMatch(likedUser -> likedUser.getId().equals(user.getId()));

            if (!userAlreadyLiked) {
                post.addLikedByUser(user);
                user.addLikedPost(post);
                postRepository.save(post);
                userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // User already liked
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Post not found
        }
    }

    /**
     * Adds a comment to a post.
     *
     * @param postId The ID of the post to be commented on.
     * @param userId The ID of the user commenting.
     * @param text   The text of the comment.
     * @return ResponseEntity with the saved comment and HTTP status.
     */

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Comment> addComment(
            @PathVariable("postId") Long postId,
            @RequestParam("userId") Long userId,
            @RequestParam("text") String text) {

        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalPost.isPresent() && optionalUser.isPresent()) {
            Post post = optionalPost.get();
            User user = optionalUser.get();

            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(user);
            comment.setText(text);

            Comment savedComment = commentRepository.save(comment);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
