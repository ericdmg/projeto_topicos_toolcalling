package org.example.models;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.SystemMessage;

public interface TransferService {

    @SystemMessage("""
        Você é um agente bancário especialista em transferências.
        Sua ÚNICA tarefa é analisar a instrução do usuário e usar as ferramentas disponíveis para executar as operações de transferência, impostos e retorno.
        Você DEVE retornar a resposta final do usuário somente após todas as chamadas de ferramentas serem concluídas e processadas.
        Não gere nenhuma explicação, resumo ou passo-a-passo.
        """)
    @UserMessage("""
        Transferir 1000 da conta BC12345 para a conta ND87632 retirando da primeira
        e depositando na segunda. Se ambas as operações forem bem-sucedidas, cobrar 1.50 da primeira
        conta. Se não, retornar o valor para a conta e não cobrar o imposto.
        """)
    String executeTransfer();
}