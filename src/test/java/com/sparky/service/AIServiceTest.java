package com.sparky.service;

import com.sparky.Domain.RequestLog;
import com.sparky.Domain.User;
import com.sparky.repository.RequestLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AIServiceTest {

    @InjectMocks
    private AIService aiService;

    @Mock
    private RequestLogRepository requestLogRepository;

    @Mock
    private MultipartFile file;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id(1L)
                .email("test@sparky.com")
                .username("tester")
                .build();
    }

    @Test
    public void testHandleAIRequest_OpenAI() {
        String response = aiService.handleAIRequest(user, "Hola", null, "openai:gpt");
        assertEquals("Respuesta generada por GPT", response);
        verify(requestLogRepository, times(1)).save(any(RequestLog.class));
    }

    @Test
    public void testHandleAIRequest_Meta() {
        String response = aiService.handleAIRequest(user, "Qué es Meta?", null, "meta:llama2");
        assertEquals("Respuesta generada por LLaMA2", response);
        verify(requestLogRepository).save(any(RequestLog.class));
    }

    @Test
    public void testHandleAIRequest_Multimodal() {
        when(file.getOriginalFilename()).thenReturn("imagen.jpg");
        String response = aiService.handleAIRequest(user, "Describe esto", file, "multimodal:model");
        assertEquals("Respuesta multimodal: texto + imagen", response);
        verify(requestLogRepository).save(argThat(log -> "imagen.jpg".equals(log.getFilename())));
    }

    @Test
    public void testHandleAIRequest_DeepSpeak() {
        String response = aiService.handleAIRequest(user, "Di algo", null, "deepspeak:voice");
        assertEquals("Respuesta por voz generada", response);
    }

    @Test
    public void testHandleAIRequest_ModelNotRecognized() {
        String response = aiService.handleAIRequest(user, "???", null, "unknown:model");
        assertEquals("Modelo no reconocido", response);
    }

    @Test
    public void testGetUserHistory() {
        when(requestLogRepository.findByUserId(1L)).thenReturn(List.of(new RequestLog()));
        List<RequestLog> history = aiService.getUserHistory(1L);
        assertEquals(1, history.size());
        verify(requestLogRepository).findByUserId(1L);
    }
}
