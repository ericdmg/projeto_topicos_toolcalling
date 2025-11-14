package org.example.deepseektoolcalling.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.P;
import org.example.deepseektoolcalling.logging.ToolLogger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("conf-b") // <--- Only load this tool when 'conf-b' is active
public class BankToolsB {

    // Must be final, and injected by Spring
    private final ToolLogger logger;

    // Constructor Injection: Spring will provide the ToolLogger bean
    public BankToolsB(ToolLogger logger) {
        this.logger = logger;
    }
    @Tool("Execute an operation in an account with a given value and return if the operation was successful or not")
    public boolean executeOperation(
            @P("WITHDRAW if withdraw, DEPOSIT if deposit, TAX, RETURN, PAYMENT") OperationType type,
            @P("account number") String accountNumber,
            @P("value to be used in the operation") double value) {
        logger.record("BankToolsB.executeOperation(" + type + ")", accountNumber, value);
        return logger.getScenarioResult(type.name().toLowerCase(), accountNumber, value);
    }
}
