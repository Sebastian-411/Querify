package com.example.querifybackend.controller;

import com.example.querifybackend.model.Comment;
import com.example.querifybackend.repository.CommentRepository;
import com.example.querifybackend.repository.PostRepository;
import com.example.querifybackend.repository.UserRepository;
import com.example.querifybackend.model.Post;
import com.example.querifybackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postRepository.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Post> savePost(@RequestBody Post post, @PathVariable("userId") Long userId) {
        // Verificamos si el usuario asociado al post existe
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            post.setUser(user.get());
            Post savedPost = postRepository.save(post);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        postRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<Void> likePost(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Usuario no encontrado
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
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Usuario ya dio like
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Post no encontrado
        }
    }


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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Post o User no encontrado
        }
    }



}
