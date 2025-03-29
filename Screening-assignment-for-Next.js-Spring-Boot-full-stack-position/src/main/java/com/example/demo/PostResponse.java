package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponse {
    private String title;
    private String body;
    private String PinggyAuthHeader;
}