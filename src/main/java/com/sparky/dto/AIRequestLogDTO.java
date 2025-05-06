package com.sparky.dto;
import java.time.LocalDateTime;

public record AIRequestLogDTO(String model, String prompt, String result, int tokens, LocalDateTime timestamp, String fileName) {}
