package com.sparky.dto;


import lombok.Data;

@Data
public class RequestDTO {
    private String query;
    private String response;
    private int tokensUsed;
    private String modelUsed;
    private String filename;
    private Long userId;
}