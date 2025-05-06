package com.sparky.service;

import com.sparky.Domain.*;
import com.sparky.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AIService {

    @Autowired
    private RequestLogRepository requestLogRepository;

    public RequestLog logRequest(User user, String query, String response, int tokens, String model, String file) {
        RequestLog log = RequestLog.builder()
                .user(user)
                .query(query)
                .response(response)
                .tokensUsed(tokens)
                .timestamp(LocalDateTime.now())
                .modelUsed(model)
                .filename(file)
                .build();
        return requestLogRepository.save(log);
    }

    public List<RequestLog> getUserHistory(Long userId) {
        return requestLogRepository.findByUserId(userId);
    }
}