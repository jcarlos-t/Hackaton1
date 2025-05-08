package com.sparky.controller;
import com.sparky.Domain.RequestLog;
import com.sparky.Domain.User;
import com.sparky.dto.AIRequestDTO;
import com.sparky.repository.UserRepository;
import com.sparky.service.AIService;
import com.sparky.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired private AIService aiService;
    @Autowired private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@Valid @RequestBody AIRequestDTO request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        String result = aiService.handleAIRequest(user, request.getPrompt(), null, request.getModel());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/completion")
    public ResponseEntity<String> completion(@Valid @RequestBody AIRequestDTO request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        String result = aiService.handleAIRequest(user, request.getPrompt(), null, request.getModel());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/multimodal")
    public ResponseEntity<String> multimodal(
            @RequestParam("prompt") String prompt,
            @RequestParam("model") String model,
            @RequestPart("image") MultipartFile image,
            Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        String result = aiService.handleAIRequest(user, prompt, image, model);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/models")
    public ResponseEntity<List<String>> getModels() {
        return ResponseEntity.ok(List.of(
                "openai/o4-mini",
                "meta/llama-2-7b-chat",
                "deepseek-ai/deepseek-llm-7b",
                "your-org/your-multimodal-model"
        ));
    }

    @GetMapping("/history")
    public ResponseEntity<List<RequestLog>> getHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(aiService.getUserHistory(userId));
    }
}
