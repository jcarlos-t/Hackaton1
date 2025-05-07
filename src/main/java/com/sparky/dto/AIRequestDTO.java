package com.sparky.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AIRequestDTO {
    @NotBlank
    private String model;

    @NotBlank
    private String prompt;
}
