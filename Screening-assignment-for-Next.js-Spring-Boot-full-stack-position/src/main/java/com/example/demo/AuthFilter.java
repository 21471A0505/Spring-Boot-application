package com.example.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    public static final ThreadLocal<String> authHeader = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader("PinggyAuthHeader");
        if (headerValue == null || headerValue.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        authHeader.set(headerValue);
        filterChain.doFilter(request, response);
        authHeader.remove();
    }
}