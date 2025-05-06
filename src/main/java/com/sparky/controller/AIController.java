package com.sparky.controller;


import com.sparky.Domain.RequestLog;
import com.sparky.Domain.User;
import com.sparky.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/chat")
    public ResponseEntity<RequestLog> chat(@RequestBody String prompt) {
        // Simular llamada a modelo AI y logueo
        return ResponseEntity.ok(aiService.logRequest(new User(), prompt, "respuesta", 50, "openai:gpt", null));
    }

    @PostMapping("/completion")
    public ResponseEntity<RequestLog> complete(@RequestBody String prompt) {
        return ResponseEntity.ok(aiService.logRequest(new User(), prompt, "completado", 60, "meta:llama2", null));
    }

    @PostMapping("/multimodal")
    public ResponseEntity<RequestLog> multimodal(@RequestBody String prompt) {
        return ResponseEntity.ok(aiService.logRequest(new User(), prompt, "imagen + texto", 90, "multimodal:model", "img.png"));
    }

    @GetMapping("/models")
    public ResponseEntity<List<String>> getModels() {
        return ResponseEntity.ok(List.of("openai:gpt", "meta:llama2", "multimodal:model", "deepspeak:voice"));
    }

    @GetMapping("/history")
    public ResponseEntity<List<RequestLog>> getHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(aiService.getUserHistory(userId));
    }
}