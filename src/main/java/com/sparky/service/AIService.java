package com.sparky.service;

import com.sparky.Domain.RequestLog;
import com.sparky.Domain.User;
import com.sparky.Domain.GitHubModelClient;
import com.sparky.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class AIService {

    @Autowired private GitHubModelClient modelClient;
    @Autowired private RequestLogRepository requestLogRepository;

    private static final Set<String> SUPPORTED_MODELS = Set.of(
            "openai/o4-mini",
            "meta/llama-2-7b-chat",
            "deepseek-ai/deepseek-llm-7b",
            "your-org/your-multimodal-model"
    );

    public String handleAIRequest(User user, String prompt, MultipartFile file, String model) {
        if (!SUPPORTED_MODELS.contains(model)) {
            throw new IllegalArgumentException("Modelo no soportado: " + model);
        }

        String result = (file != null)
                ? modelClient.sendMultimodal(model, prompt, file)
                : modelClient.sendPrompt(model, prompt);

        String fileName = (file != null) ? file.getOriginalFilename() : null;

        requestLogRepository.save(RequestLog.builder()
                .user(user)
                .query(prompt)
                .response(result)
                .tokensUsed(100)
                .timestamp(LocalDateTime.now())
                .modelUsed(model)
                .filename(fileName)
                .build());

        return result;
    }

    public List<RequestLog> getUserHistory(Long userId) {
        return requestLogRepository.findByUserId(userId);
    }
}
