package com.sparky.dto;


import lombok.Data;

@Data
public class UserLimitDTO {
    private String modelName;
    private int remainingRequests;
    private int remainingTokens;
    private Long userId;
}
