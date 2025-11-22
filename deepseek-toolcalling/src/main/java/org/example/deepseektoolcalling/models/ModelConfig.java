package org.example.deepseektoolcalling.models;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class ModelConfig {

    @Value("${langchain4j.ollama.chat-model.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${langchain4j.ollama.chat-model.model-name:llama3.2:latest}")
    private String modelName;

    @Value("${langchain4j.ollama.chat-model.temperature:0.0}")
    private double temperature;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(temperature)
                .build();
    }

    private String readResource(Resource resource) {
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read prompt file: " + resource.getFilename(), e);
        }
    }

    @Bean
    public String prompt1(@Value("classpath:prompt1.txt") Resource resource) {
        return readResource(resource);
    }

    @Bean
    public String prompt2(@Value("classpath:prompt2.txt") Resource resource) {
        return readResource(resource);
    }

    @Bean
    public String prompt3(@Value("classpath:prompt3.txt") Resource resource) {
        return readResource(resource);
    }
}
