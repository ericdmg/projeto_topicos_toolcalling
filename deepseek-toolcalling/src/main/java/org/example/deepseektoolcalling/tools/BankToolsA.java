package org.example.deepseektoolcalling.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.P;
import org.example.deepseektoolcalling.logging.ToolLogger;
import org.springframework.stereotype.Component;

@Component
public class BankToolsA {

    // Must be final, and injected by Spring
    private final ToolLogger logger;

    // Constructor Injection: Spring will provide the ToolLogger bean
    public BankToolsA(ToolLogger logger) {
        this.logger = logger;
    }

    @Tool("Withdraw a value from an account and return if the operation was successful or not")
    public boolean withdraw(@P("account number") String accountNumber,
                            @P("value to be withdraw") double value) {
        logger.record("BankToolsA.withdraw", accountNumber, value);
        return logger.getScenarioResult("withdraw", accountNumber, value);
    }

    @Tool("Deposit the value into an account and return if the operation was successful or not")
    public boolean deposit(@P("account number") String accountNumber,
                           @P("value to be deposited") double value) {
        logger.record("BankToolsA.deposit", accountNumber, value);
        return logger.getScenarioResult("deposit", accountNumber, value);
    }

    @Tool("Perform a payment with a value using the money from an account and return if the operation was successful or not")
    public boolean payment(@P("account number") String accountNumber,
                           @P("value of the payment") double value) {
        logger.record("BankToolsA.payment", accountNumber, value);
        return logger.getScenarioResult("payment", accountNumber, value);
    }

    @Tool("Charge the value of a tax from the account and return if the operation was successful or not")
    public boolean taxes(@P("account number") String accountNumber,
                         @P("value of the tax") double value) {
        logger.record("BankToolsA.taxes", accountNumber, value);
        return logger.getScenarioResult("taxes", accountNumber, value);
    }

    @Tool("Return a value of a failed operation to an account and return if the operation was successful or not")
    public boolean returnValue(@P("account number") String accountNumber,
                               @P("value to be returned") double value) {
        logger.record("BankToolsA.returnValue", accountNumber, value);
        return logger.getScenarioResult("returnValue", accountNumber, value);
    }
}