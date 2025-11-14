package org.example.deepseektoolcalling.models;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.example.deepseektoolcalling.tools.BankToolsA;
import org.example.deepseektoolcalling.tools.BankToolsB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AgentConfig {

    /**
     * Método helper para criar a instância de TransferService com as ferramentas fornecidas.
     */
    private TransferService createAgent(ChatLanguageModel model, List<Object> tools) {
        return AiServices.builder(TransferService.class)
                .chatLanguageModel(model)
                .tools(tools)
                .build();
    }

    // --- CONF1: BankToolsA only ---
    @Bean
    public TransferService agentConf1(ChatLanguageModel model, BankToolsA toolA) {
        return createAgent(model, List.of(toolA));
    }

    // --- CONF2: BankToolsB only ---
    @Bean
    public TransferService agentConf2(ChatLanguageModel model, BankToolsB toolB) {
        return createAgent(model, List.of(toolB));
    }

    // --- CONF3: BankToolsA, BankToolsB ---
    @Bean
    public TransferService agentConf3(ChatLanguageModel model, BankToolsA toolA, BankToolsB toolB) {
        return createAgent(model, List.of(toolA, toolB));
    }

    // --- CONF4: BankToolsB, BankToolsA ---
    @Bean
    public TransferService agentConf4(ChatLanguageModel model, BankToolsA toolA, BankToolsB toolB) {
        return createAgent(model, List.of(toolB, toolA));
    }
}