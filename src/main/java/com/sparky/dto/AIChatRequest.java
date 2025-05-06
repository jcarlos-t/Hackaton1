package com.sparky.dto;
import jakarta.validation.constraints.NotBlank;

public record AIChatRequest(@NotBlank String model, @NotBlank String prompt) {}
