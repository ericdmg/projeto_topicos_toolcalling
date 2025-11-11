package org.example.models;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.example.logging.ToolLogger;
import org.example.tools.*;

public class ModelRunner {

    private final ToolLogger logger = ToolLogger.getInstance();

    public void run(String modelName, String prompt, Object... tools) {
        // Create the Ollama model
        var model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName(modelName)
                .build();

        // Combine prompt with a description of available tools
        StringBuilder systemPrompt = new StringBuilder("You have access to the following tools:\n");
        for (Object tool : tools) {
            systemPrompt.append("- ").append(tool.getClass().getSimpleName()).append("\n");
        }
        systemPrompt.append("\n---\nNow follow the user instructions below:\n\n");

        String finalPrompt = systemPrompt + prompt;

        // Call the model
        var response = model.generate(finalPrompt);
        System.out.println("Model response:\n" + response);

        // Simulate that we would have logged a tool invocation
        logger.record("ModelResponse", "N/A", 0);
    }
}
