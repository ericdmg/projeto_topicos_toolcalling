package org.example.deepseektoolcalling;

import org.example.deepseektoolcalling.logging.ScenarioSimulator;
import org.example.deepseektoolcalling.logging.ToolLogger;
import org.example.deepseektoolcalling.models.TransferService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Main implements CommandLineRunner {

    private final ToolLogger logger;
    private final ScenarioSimulator simulator;
    private final TransferService agentConf1;
    private final TransferService agentConf2;
    private final TransferService agentConf3;
    private final TransferService agentConf4;
    private final String prompt1;
    private final String prompt2;
    private final String prompt3;

    public Main(
            ToolLogger logger,
            ScenarioSimulator simulator,
            @Qualifier("agentConf1") TransferService agentConf1,
            @Qualifier("agentConf2") TransferService agentConf2,
            @Qualifier("agentConf3") TransferService agentConf3,
            @Qualifier("agentConf4") TransferService agentConf4,
            @Qualifier("prompt1") String prompt1,
            @Qualifier("prompt2") String prompt2,
            @Qualifier("prompt3") String prompt3) {

        this.logger = logger;
        this.simulator = simulator;
        this.agentConf1 = agentConf1;
        this.agentConf2 = agentConf2;
        this.agentConf3 = agentConf3;
        this.agentConf4 = agentConf4;
        this.prompt1 = prompt1;
        this.prompt2 = prompt2;
        this.prompt3 = prompt3;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("====================================================");
        System.out.println("🚀 INICIANDO EXECUÇÃO SISTEMÁTICA DE TESTES");
        System.out.println("====================================================");

        Map<String, TransferService> agents = Map.of(
                "CONF1", this.agentConf1,
                "CONF2", this.agentConf2,
                "CONF3", this.agentConf3,
                "CONF4", this.agentConf4
        );

        // Itera sobre as configurações em ordem (CONF1, CONF2, ...)
        for (Map.Entry<String, TransferService> agentEntry :
                agents.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .toList()) {

            String confName = agentEntry.getKey(); // Ex: "CONF1"
            TransferService agent = agentEntry.getValue();

            System.out.println("\n=============================================");
            System.out.printf("🔬 INICIANDO TESTES PARA: %s\n", confName);
            System.out.println("=============================================");


            this.runPrompt1Tests(confName, agent);

            this.runPrompt2Tests(confName, agent);

            this.runPrompt3Tests(confName, agent);
        }

        System.out.println("\n====================================================");
        System.out.println("✅ EXECUÇÃO DE TESTES CONCLUÍDA");
        System.out.println("====================================================");
    }

    private void runPrompt1Tests(String confName, TransferService agent) {
        System.out.printf("\n>>> TESTANDO PROMPT 1: Transferência Condicional...\n");

        // Cenário 1A
        System.out.println("\n--- Cenário 1A: SUCESSO TOTAL (10 Repetições) ---");
        this.simulator.configureP1A(confName);
        this.executeTestRun(confName, agent, this.prompt1, "SCENARIO1A");

        // Cenário 1B
        System.out.println("\n--- Cenário 1B: FALHA PARCIAL (10 Repetições) ---");
        this.simulator.configureP1B(confName);
        this.executeTestRun(confName, agent, this.prompt1, "SCENARIO1B");
    }

    private void runPrompt2Tests(String confName, TransferService agent) {
        System.out.printf("\n>>> TESTANDO PROMPT 2: Loop de Saques...\n");

        // Cenário 2A
        System.out.println("\n--- Cenário 2A: SUCESSO TOTAL (10 Repetições) ---");
        this.simulator.configureP2A(confName);
        this.executeTestRun(confName, agent, this.prompt2, "SCENARIO2A");

        // Cenário 2B
        System.out.println("\n--- Cenário 2B: FALHA PARCIAL (10 Repetições) ---");
        this.simulator.configureP2B(confName);
        this.executeTestRun(confName, agent, this.prompt2, "SCENARIO2B");
    }

    private void runPrompt3Tests(String confName, TransferService agent) {
        System.out.printf("\n>>> TESTANDO PROMPT 3: Saques Paralelos...\n");

        // Cenário 3A
        System.out.println("\n--- Cenário 3A: SUCESSO TOTAL (10 Repetições) ---");
        this.simulator.configureP3A(confName);
        this.executeTestRun(confName, agent, this.prompt3, "SCENARIO3A");

        // Cenário 3B
        System.out.println("\n--- Cenário 3B: FALHA PARCIAL (10 Repetições) ---");
        this.simulator.configureP3B(confName);
        this.executeTestRun(confName, agent, this.prompt3, "SCENARIO3B");
    }

    private void executeTestRun(String confName, TransferService agent, String prompt, String scenario) {
        System.out.printf("\n--- EXECUÇÃO: %s, Cenário: %s ---\n", confName, scenario);
        for (int i = 1; i <= 10; i++) {
            this.logger.clear(); // Limpa logs para esta execução
            try {
                String result = agent.executePrompt(prompt);
                System.out.printf("  Run %d: OK. Chamadas: %s, Resposta Final: %s\n",
                        i, this.logger.getRecords(), result.trim());
            } catch (Exception e) {
                System.err.printf("  Run %d: FALHA - Erro: %s\n", i, e.getMessage());
            }
        }
    }
}