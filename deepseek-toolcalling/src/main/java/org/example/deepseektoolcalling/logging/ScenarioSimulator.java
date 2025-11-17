package org.example.deepseektoolcalling.logging;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ScenarioSimulator {

    private Map<String, Boolean> scenarioResults = new HashMap<>();
    private int withdrawCounter = 0;
    private int failAfterCount = -1;

    /**
     * Reseta todo o estado do simulador (mapa e contadores).
     */
    private void resetState() {
        this.scenarioResults.clear();
        this.withdrawCounter = 0;
        this.failAfterCount = -1;
    }

    // --- MÉTODOS DE CONFIGURAÇÃO GENÉRICOS ---
    // A Main agora chama apenas estes métodos, passando a 'confName'.

    public void configureP1A(String confName) {
        this.resetState();

        if (confName.equals("CONF2")) {
            // --- Configuração para BankToolsB ---
            this.scenarioResults.put("executeOperation:BC12345:1000.0", true); // WITHDRAW
            this.scenarioResults.put("executeOperation:ND87632:1000.0", true); // DEPOSIT
            this.scenarioResults.put("executeOperation:BC12345:1.50", true);  // TAX
        } else {
            // --- Padrão para CONF1, CONF3, CONF4 ---
            this.scenarioResults.put("withdraw:BC12345:1000.0", true);
            this.scenarioResults.put("deposit:ND87632:1000.0", true);
            this.scenarioResults.put("taxes:BC12345:1.50", true);
        }
    }

    public void configureP1B(String confName) {
        this.resetState();

        if (confName.equals("CONF2")) {
            // --- Configuração para BankToolsB ---
            this.scenarioResults.put("executeOperation:BC12345:1000.0", true);  // WITHDRAW (Sucesso)
            this.scenarioResults.put("executeOperation:ND87632:1000.0", false); // DEPOSIT (Falha)
            this.scenarioResults.put("executeOperation:BC12345:1000.0", true);  // RETURN
        } else {
            // --- Padrão para CONF1, CONF3, CONF4 ---
            this.scenarioResults.put("withdraw:BC12345:1000.0", true);
            this.scenarioResults.put("deposit:ND87632:1000.0", false); // Falha
            this.scenarioResults.put("returnValue:BC12345:1000.0", true);
        }
    }

    public void configureP2A(String confName) {
        this.resetState();

        if (confName.equals("CONF2")) {
            // --- Configuração para BankToolsB ---
            this.scenarioResults.put("executeOperation:FG62495S:2500.0", true); // DEPOSIT
            this.scenarioResults.put("executeOperation:FG62495S:250.0", true);  // TAX
        } else {
            // --- Padrão para CONF1, CONF3, CONF4 ---
            this.scenarioResults.put("deposit:FG62495S:2500.0", true); // 5 * 500
            this.scenarioResults.put("taxes:FG62495S:250.0", true);   // 10%
        }
    }

    public void configureP2B(String confName) {
        this.resetState();
        this.failAfterCount = 3; // Lógica de falha (genérica)

        if (confName.equals("CONF2")) {
            // --- Configuração para BankToolsB ---
            this.scenarioResults.put("executeOperation:FG62495S:1500.0", true); // DEPOSIT
            this.scenarioResults.put("executeOperation:FG62495S:150.0", true);  // TAX
        } else {
            // --- Padrão para CONF1, CONF3, CONF4 ---
            this.scenarioResults.put("deposit:FG62495S:1500.0", true); // 3 * 500
            this.scenarioResults.put("taxes:FG62495S:150.0", true);   // 10%
        }
    }

    public void configureP3A(String confName) {
        this.resetState();

        if (confName.equals("CONF2")) {
            // --- Configuração para BankToolsB ---
            this.scenarioResults.put("executeOperation:AG7340H:600.0", true); // WITHDRAW
            this.scenarioResults.put("executeOperation:TG23986Q:700.0", true); // WITHDRAW
            this.scenarioResults.put("executeOperation:WS2754T:1300.0", true); // DEPOSIT
            this.scenarioResults.put("executeOperation:WS2754T:1200.0", true); // PAYMENT
        } else {
            // --- Padrão para CONF1, CONF3, CONF4 ---
            this.scenarioResults.put("withdraw:AG7340H:600.0", true);
            this.scenarioResults.put("withdraw:TG23986Q:700.0", true);
            this.scenarioResults.put("deposit:WS2754T:1300.0", true); // 600 + 700
            this.scenarioResults.put("payment:WS2754T:1200.0", true);
        }
    }

    public void configureP3B(String confName) {
        this.resetState();

        if (confName.equals("CONF2")) {
            // --- Configuração para BankToolsB ---
            this.scenarioResults.put("executeOperation:AG7340H:600.0", true);  // WITHDRAW (Sucesso)
            this.scenarioResults.put("executeOperation:TG23986Q:700.0", false); // WITHDRAW (Falha)
            this.scenarioResults.put("executeOperation:AG7340H:600.0", true);  // RETURN (Correção)
        } else {
            // --- Padrão para CONF1, CONF3, CONF4 ---
            this.scenarioResults.put("withdraw:AG7340H:600.0", true);  // Sucesso
            this.scenarioResults.put("withdraw:TG23986Q:700.0", false); // Falha
            this.scenarioResults.put("returnValue:AG7340H:600.0", true); // Correção
        }
    }

    /**
     * Avalia qual resultado booleano retornar.
     * Esta lógica é genérica e funciona para todos os cenários.
     */
    public boolean evaluate(String operation, String account, double value) {
        String key = String.format("%s:%s:%.1f", operation, account, value);

        // Lógica Stateful (Contagem) para PROMPT 2
        if (this.failAfterCount > -1) {
            // Checa se é a chamada de saque em loop (seja da ToolsA ou ToolsB)
            boolean isP2Withdraw = (operation.equals("withdraw") || operation.equals("executeOperation"))
                    && account.equals("BC3456A") && value == 500.0;

            if (isP2Withdraw) {
                this.withdrawCounter++;
                // Retorna 'true' se o contador for MENOR OU IGUAL ao limite,
                // Retorna 'false' se estourar.
                return this.withdrawCounter <= this.failAfterCount;
            }
        }

        // Lógica Padrão (Stateless) para P1 e P3
        return this.scenarioResults.getOrDefault(key, true);
    }
}