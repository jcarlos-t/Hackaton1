package com.sparky.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AIMultimodalRequest(@NotBlank String model, @NotBlank String prompt, @NotNull MultipartFile image) {}
