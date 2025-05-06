package com.sparky.service;

import com.sparky.ai.dto.*;
import com.sparky.entity.RequestLog;
import com.sparky.entity.User;
import com.sparky.repository.RequestLogRepository;
import com.sparky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AIService {

    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.url}")
    private String apiUrl;

    @Autowired
    private RequestLogRepository logRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private LimitService limitService;

    private final RestTemplate restTemplate = new RestTemplate();

    public AIResponse chat(Long userId, AIChatRequest req) {
        validateAccess(userId, req.model());
        HttpEntity<?> http = buildRequest(req.prompt());
        String endpoint = apiUrl + "/chat";
        ResponseEntity<String> res = restTemplate.exchange(endpoint, HttpMethod.POST, http, String.class);
        return handleResponse(userId, req.prompt(), res.getBody(), req.model());
    }

    public AIResponse completion(Long userId, AICompletionRequest req) {
        validateAccess(userId, req.model());
        HttpEntity<?> http = buildRequest(req.prompt());
        String endpoint = apiUrl + "/completion";
        ResponseEntity<String> res = restTemplate.exchange(endpoint, HttpMethod.POST, http, String.class);
        return handleResponse(userId, req.prompt(), res.getBody(), req.model());
    }

    public AIResponse multimodal(Long userId, AIMultimodalRequest req) throws IOException {
        if (req.image() == null || req.image().isEmpty()) {
            throw new IllegalArgumentException("Imagen requerida.");
        }
        validateAccess(userId, req.model());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("prompt", req.prompt());
        body.add("image", new ByteArrayResource(req.image().getBytes()) {
            @Override
            public String getFilename() {
                return req.image().getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> res = restTemplate.exchange(apiUrl + "/multimodal", HttpMethod.POST, request, String.class);

        return handleResponse(userId, req.prompt(), res.getBody(), req.model(), req.image().getOriginalFilename());
    }

    private HttpEntity<?> buildRequest(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, String> json = new HashMap<>();
        json.put("prompt", prompt);

        return new HttpEntity<>(json, headers);
    }

    private void validateAccess(Long userId, String model) {
        limitService.validate(userId, model);
    }

    private AIResponse handleResponse(Long userId, String prompt, String rawResponse, String model) {
        return handleResponse(userId, prompt, rawResponse, model, null);
    }

    private AIResponse handleResponse(Long userId, String prompt, String rawResponse, String model, String fileName) {
        int tokens = rawResponse.length() / 4;
        User user = userRepo.findById(userId).orElseThrow();

        RequestLog log = new RequestLog();
        log.setQuery(prompt);
        log.setResponse(rawResponse);
        log.setTokens(tokens);
        log.setTimestamp(LocalDateTime.now());
        log.setModelType(model);
        log.setUser(user);
        log.setCompany(user.getCompany());
        log.setFileName(fileName);

        logRepo.save(log);
        return new AIResponse(rawResponse, tokens, model);
    }

    public List<AIRequestLogDTO> getHistory(Long userId) {
        List<RequestLog> logs = logRepo.findByUserIdOrderByTimestampDesc(userId);
        return logs.stream().map(log -> new AIRequestLogDTO(
            log.getModelType(),
            log.getQuery(),
            log.getResponse(),
            log.getTokens(),
            log.getTimestamp(),
            log.getFileName()
        )).toList();
    }
}
