package org.example.deepseektoolcalling;

import org.example.deepseektoolcalling.logging.ScenarioSimulator;
import org.example.deepseektoolcalling.logging.ToolLogger;
import org.example.deepseektoolcalling.models.TransferService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {

    private final ToolLogger logger;
    private final ScenarioSimulator simulator;

    // --- Injeção dos 4 Agentes (Mesmo que só usemos o agentConf1) ---
    private final TransferService agentConf1;
    private final TransferService agentConf2;
    private final TransferService agentConf3;
    private final TransferService agentConf4;

    // --- Injeção dos 3 Prompts ---
    private final String prompt1;
    private final String prompt2;
    private final String prompt3;

    // Construtor com Injeção de todos os Beans
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

    /**
     * Executa o teste por 10 repetições para medir a Consistência.
     */
    private void executeTestRun(String confName, TransferService agent, String prompt, String scenario) {

        System.out.printf("\n--- EXECUÇÃO: %s, Cenário: %s ---\n", confName, scenario);

        // O loop para 10 execuções deve estar DENTRO deste método.
        for (int i = 1; i <= 10; i++) {
            logger.clear(); // Limpa logs a cada nova execução (reset)

            try {
                String result = agent.executePrompt(prompt);

                // Limita a resposta para o log ser legível
                String finalResponse = result.trim().substring(0, Math.min(result.trim().length(), 60)) + "...";

                // O log de Chamadas: logger.getRecords() mostra a sequência de Tool Calling
                System.out.printf("  Run %d: OK. Chamadas: %s, Resposta Final: %s\n",
                        i, logger.getRecords(), finalResponse);

            } catch (Exception e) {
                System.err.printf("  Run %d: FALHA - Erro: %s\n", i, e.getMessage());
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=============================================");
        System.out.println("🔬 INICIANDO TESTE ISOLADO: CONF1 (BankToolsA)");
        System.out.println("=============================================");

        // Mapeamento explícito para CONF1
        TransferService agent = agentConf1; // Usa o agente configurado com SOMENTE ToolsA
        String confName = "CONF1 (A)";

        // --- TESTE: PROMPT 1 (Transferência Condicional) ---
        System.out.println("\n>>> TESTANDO PROMPT 1: Transferência Condicional");

        // 1. SCENARIO 1A: Sucesso Total
        System.out.println("\n--- Cenário 1A: SUCESSO TOTAL (10 Repetições) ---");
        simulator.configureScenario1A(); // Configura o simulador (withdraw=true, deposit=true, taxes=true)

        // Chama o método executeTestRun, que fará o loop de 10x internamente
        executeTestRun(confName, agent, prompt1, "SCENARIO1A");

        // 2. SCENARIO 1B: Falha Parcial (Depósito Falha)
        System.out.println("\n--- Cenário 1B: FALHA PARCIAL (Depósito) (10 Repetições) ---");
        simulator.configureScenario1B(); // Configura o simulador (deposit=false)

        // Chama o método executeTestRun para 10 repetições
        executeTestRun(confName, agent, prompt1, "SCENARIO1B");


        // O restante da lógica deve ser adicionado aqui para os outros PROMPTS/CENÁRIOS,
        // mas o teste inicial isolado está correto desta forma.

        System.out.println("\n=============================================");
        System.out.println("✅ Teste Isolado de CONF1 Concluído.");
    }
}