package org.example.logging;

public class ScenarioSimulator {
    // Later you can configure which operations succeed or fail for each scenario.
    public boolean evaluate(String operation, String account, double value) {
        // default: everything succeeds
        return true;
    }
}
