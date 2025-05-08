package com.sparky.Domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class GitHubModelClient {

    @Value("${github.token}")
    private String githubToken;

    @Value("${github.model.url:https://models.github.ai/inference}")
    private String endpoint;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendPrompt(String model, String prompt) {
        String url = endpoint;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", new Object[] {
                Map.of("role", "developer", "content", "You are a helpful assistant."),
                Map.of("role", "user", "content", prompt)
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(githubToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url + "/chat/completions",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map<String, Object> choice = ((Map<String, Object>) ((Map<String, Object>) ((java.util.List<?>) response.getBody().get("choices")).get(0)).get("message"));
        return choice.get("content").toString();
    }

    public String sendMultimodal(String model, String prompt, MultipartFile image) {
        String url = endpoint + "/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(githubToken);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", model);
        body.add("prompt", prompt);

        try {
            ByteArrayResource imageResource = new ByteArrayResource(image.getBytes()) {
                @Override
                public String getFilename() {
                    return image.getOriginalFilename();
                }
            };

            body.add("image", new HttpEntity<>(imageResource, new HttpHeaders()));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de imagen", e);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

        Map<String, Object> choice = ((Map<String, Object>) ((Map<String, Object>) ((java.util.List<?>) response.getBody().get("choices")).get(0)).get("message"));
        return choice.get("content").toString();
    }
}
