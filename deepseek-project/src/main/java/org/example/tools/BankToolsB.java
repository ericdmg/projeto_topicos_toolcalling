package org.example.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.P;
import org.example.logging.ToolLogger;
import org.example.tools.OperationType;

public class BankToolsB {

    private ToolLogger logger = ToolLogger.getInstance();

    @Tool("Execute an operation in an account with a given value and return if the operation was successful or not")
    public boolean executeOperation(
            @P("WITHDRAW if withdraw, DEPOSIT if deposit, TAX, RETURN, PAYMENT") OperationType type,
            @P("account number") String accountNumber,
            @P("value to be used in the operation") double value) {
        logger.record("BankToolsB.executeOperation(" + type + ")", accountNumber, value);
        return logger.getScenarioResult(type.name().toLowerCase(), accountNumber, value);
    }
}
