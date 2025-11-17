package org.example.deepseektoolcalling.models;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import org.springframework.stereotype.Component;

@AiService
public interface TransferService {
    // Define a persona e força o uso das ferramentas
    @SystemMessage("""
        You are an expert bank agent. Your sole purpose is to analyze the user's request and execute the necessary transfer, tax, and refund logic using the available tools.
        You MUST achieve the final goal by calling the tools in the correct sequence.
        Return the final numerical result of the operation ONLY after all tools have been executed and the final state is known.
        
        CRITICAL RULE: DO NOT generate any code (Python, Java, pseudo-code) or step-by-step reasoning in your final answer.
        CRITICAL RULE: After executing the final tool call, you MUST provide a concise, textual summary of the operation's outcome to the user.
        """)

    String executePrompt(String userPrompt);
}