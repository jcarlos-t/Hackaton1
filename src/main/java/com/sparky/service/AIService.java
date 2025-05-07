package com.sparky.service;

import com.sparky.Domain.RequestLog;
import com.sparky.Domain.User;
import com.sparky.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AIService {

    @Autowired
    private RequestLogRepository requestLogRepository;

    public String handleAIRequest(User user, String prompt, MultipartFile file, String model) {
        // Aquí puede ir lógica real para llamar al modelo AI externo

        String simulatedResponse = switch (model) {
            case "openai:gpt" -> "Respuesta generada por GPT";
            case "meta:llama2" -> "Respuesta generada por LLaMA2";
            case "multimodal:model" -> "Respuesta multimodal: texto + imagen";
            case "deepspeak:voice" -> "Respuesta por voz generada";
            default -> "Modelo no reconocido";
        };

        String fileName = (file != null) ? file.getOriginalFilename() : null;

        requestLogRepository.save(RequestLog.builder()
                .user(user)
                .query(prompt)
                .response(simulatedResponse)
                .tokensUsed(100)
                .timestamp(LocalDateTime.now())
                .modelUsed(model)
                .filename(fileName)
                .build());

        return simulatedResponse;
    }

    public List<RequestLog> getUserHistory(Long userId) {
        return requestLogRepository.findByUserId(userId);
    }
}
