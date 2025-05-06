package com.sparky.controller;

import com.sparky.ai.dto.*;
import com.sparky.ai.Service.AIService;
import com.sparky.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@PreAuthorize("hasRole('ROLE_USER')")
public class AIController {

    @Autowired private AIService aiService;

    @PostMapping("/chat")
    public ResponseEntity<AIResponse> chat(@AuthenticationPrincipal User user,
                                           @RequestBody @Valid AIChatRequest req) {
        return ResponseEntity.ok(aiService.chat(user.getId(), req));
    }

    @PostMapping("/completion")
    public ResponseEntity<AIResponse> completion(@AuthenticationPrincipal User user,
                                                 @RequestBody @Valid AICompletionRequest req) {
        return ResponseEntity.ok(aiService.completion(user.getId(), req));
    }

    @PostMapping(value = "/multimodal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AIResponse> multimodal(@AuthenticationPrincipal User user,
                                                 @ModelAttribute @Valid AIMultimodalRequest req) throws IOException {
        return ResponseEntity.ok(aiService.multimodal(user.getId(), req));
    }

    @GetMapping("/models")
    public ResponseEntity<List<String>> availableModels() {
        return ResponseEntity.ok(List.of("openai-gpt4", "meta-llama", "deepspeak", "github-multimodal"));
    }

    @GetMapping("/history")
    public ResponseEntity<List<AIRequestLogDTO>> history(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(aiService.getHistory(user.getId()));
    }
}
