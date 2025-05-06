package com.sparky.dto;


import lombok.Data;

@Data
public class RestrictionDTO {
    private String modelName;
    private int maxRequests;
    private int maxTokens;
    private int windowMinutes;
    private Long companyId;
}
