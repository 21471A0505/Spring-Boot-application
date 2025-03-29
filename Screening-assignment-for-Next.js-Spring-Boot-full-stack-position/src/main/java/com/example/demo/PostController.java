package com.example.demo.controller;

import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.filter.AuthFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private final List<PostResponse> posts = new ArrayList<>();

    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody PostRequest request) {
        String headerValue = AuthFilter.authHeader.get();
        posts.add(new PostResponse(request.getTitle(), request.getBody(), headerValue));
        return ResponseEntity.ok("Post saved successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<PostResponse>> getPosts() {
        return ResponseEntity.ok(posts);
    }
}
