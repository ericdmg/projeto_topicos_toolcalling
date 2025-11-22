package org.example.deepseektoolcalling.logging;



import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToolLogger {

    private static ToolLogger instance;
    private List<InvocationRecord> records = new ArrayList<>();
    private ScenarioSimulator simulator = new ScenarioSimulator();

    public ToolLogger(ScenarioSimulator simulator) {
        this.simulator = simulator;
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
