package com.sparky.dto;
import java.time.LocalDateTime;

public record AIErrorResponse(String error, LocalDateTime timestamp) {}
