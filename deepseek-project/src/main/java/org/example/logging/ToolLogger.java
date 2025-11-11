package org.example.logging;

import org.example.logging.InvocationRecord;
import org.example.logging.ScenarioSimulator;

import java.util.ArrayList;
import java.util.List;

public class ToolLogger {

    private static ToolLogger instance;
    private List<InvocationRecord> records = new ArrayList<>();
    private ScenarioSimulator simulator = new ScenarioSimulator();

    private ToolLogger() {}

    public static ToolLogger getInstance() {
        if (instance == null) instance = new ToolLogger();
        return instance;
    }

    public void record(String method, String accountNumber, double value) {
        records.add(new InvocationRecord(method, accountNumber, value));
        System.out.println("Invoked: " + method + " (" + accountNumber + ", " + value + ")");
    }

    public boolean getScenarioResult(String operation, String accountNumber, double value) {
        return simulator.evaluate(operation, accountNumber, value);
    }

    public List<InvocationRecord> getRecords() {
        return records;
    }

    public void clear() {
        records.clear();
    }
}
