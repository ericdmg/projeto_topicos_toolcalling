package org.example;

import org.example.models.ModelRunner;
import org.example.tools.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var runner = new ModelRunner();

        String prompt1 = """
            Transfer 1000 from account BC12345 to the account ND87632 by withdrawing from the first
            and depositing into the second. If both operations are successful, charge 1.50 from the first
            account. If not, return the value to the account and don't charge the tax.
        """;

        List<Object> tools = List.of(new BankToolsA(), new BankToolsB());
        // CONF1: only BankToolsA
        runner.run("deepseek-r1:1.5b", prompt1, tools);

        // CONF2: only BankToolsB
        // runner.run("deepseek-r1:latest", prompt1, new BankToolsB());

        // CONF3: both, A then B
        // runner.run("deepseek-r1:latest", prompt1, new BankToolsA(), new BankToolsB());

        // CONF4: both, B then A
        // runner.run("deepseek-r1:latest", prompt1, new BankToolsB(), new BankToolsA());
    }
}
