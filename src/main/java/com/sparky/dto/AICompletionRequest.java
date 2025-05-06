package com.sparky.dto;
import jakarta.validation.constraints.NotBlank;

public record AICompletionRequest(@NotBlank String model, @NotBlank String prompt) {}
