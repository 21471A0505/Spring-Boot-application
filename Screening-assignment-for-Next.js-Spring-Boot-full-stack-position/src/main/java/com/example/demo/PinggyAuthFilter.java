
// Backend (Spring Boot) structure & implementation for the assignment

// Filter - PinggyAuthFilter.java
package com.example.backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PinggyAuthFilter extends OncePerRequestFilter {

    public static final ThreadLocal<String> authHeader = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("PinggyAuthHeader");

        if (header == null || header.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Missing PinggyAuthHeader");
            return;
        }

        authHeader.set(header);
        try {
            filterChain.doFilter(request, response);
        } finally {
            authHeader.remove();
        }
    }
}

// DTO - PostRequest.java
package com.example.backend.dto;

public class PostRequest {
    private String title;
    private String body;

    // Getters & Setters
}

// DTO - PostResponse.java
package com.example.backend.dto;

public class PostResponse {
    private String title;
    private String body;
    private String pinggyAuthHeader;

    public PostResponse(String title, String body, String pinggyAuthHeader) {
        this.title = title;
        this.body = body;
        this.pinggyAuthHeader = pinggyAuthHeader;
    }

    // Getters & Setters
}

// Controller - PostController.java
package com.example.backend.controller;

import com.example.backend.dto.PostRequest;
import com.example.backend.dto.PostResponse;
import com.example.backend.filter.PinggyAuthFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class PostController {

    private final List<PostResponse> postList = new ArrayList<>();

    @PostMapping("post")
    public ResponseEntity<String> createPost(@RequestBody PostRequest request) {
        String authHeader = PinggyAuthFilter.authHeader.get();
        postList.add(new PostResponse(request.getTitle(), request.getBody(), authHeader));
        return ResponseEntity.ok("Post created successfully");
    }

    @GetMapping("list")
    public ResponseEntity<List<PostResponse>> getPosts() {
        return ResponseEntity.ok(postList);
    }
}

// Application - Application.java
package com.example.backend;    
