// ScenarioSimulator.java
package org.example.deepseektoolcalling.logging;

// Importe suas ferramentas para referências de método, se necessário
import org.example.deepseektoolcalling.tools.BankToolsA;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component // <--- ADICIONE ISTO
public class ScenarioSimulator {

    // Mapa para armazenar o resultado condicional
    private Map<String, Boolean> scenarioResults = new HashMap<>();

    public void configureScenario1B() {
        // SCENARIO 1B: Saque (withdraw) sucesso, Depósito (deposit) falha.
        // O valor é uma chave simplificada: "operação:conta:valor"
        scenarioResults.clear();

        // Exemplo: O saque DEVE ter sucesso
        scenarioResults.put("withdraw:BC12345:1000.0", false);

        // O depósito DEVE falhar (Cenário B)
        scenarioResults.put("deposit:ND87632:1000.0", false);

        // Outras operações podem ser definidas (taxas, return)
        scenarioResults.put("taxes:BC12345:1.50", false);
    }

    // SCENARIO 1A: Ambas as operações, saque e depósito, são executadas com sucesso.
    public void configureScenario1A() {
        scenarioResults.clear();
        // Neste cenário, o padrão (default=true) é suficiente, mas definimos
        // as chaves esperadas para garantir que o LLM as chame.
        scenarioResults.put("withdraw:BC12345:1000.0", true);
        scenarioResults.put("deposit:ND87632:1000.0", true);
        scenarioResults.put("taxes:BC12345:1.50", true);
    }

    public boolean evaluate(String operation, String account, double value) {
        // Formata a chave com valor formatado (ex: "withdraw:BC12345:1000.0")
        String key = String.format("%s:%s:%.1f", operation, account, value);

        // Retorna o valor definido no mapa OU true (sucesso) por padrão.
        // O padrão 'true' é seguro para operações não críticas não definidas explicitamente.
        return scenarioResults.getOrDefault(key, true);
    }
}