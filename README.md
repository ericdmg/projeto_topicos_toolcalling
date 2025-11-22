# Projeto — Avaliação de LLMs com TOOLs  
## PCC0126 – Tópicos Especiais em Engenharia de Software e Sistemas Computacionais I

Este repositório contém a implementação utilizada para executar os experimentos do Projeto Final da disciplina, cujo objetivo é comparar como diferentes modelos LLM interagem com TOOLs expostas via LangChain4j, considerando APIs com estruturas distintas.

---

## 🎯 Objetivo

O projeto avalia:

- **BankToolsA** — API com cinco métodos distintos  
- **BankToolsB** — API unificada com operação parametrizada  
- **Configurações mistas** (CONF3 e CONF4)

Em cada execução, registram-se:

- Métodos chamados  
- Ordem das chamadas  
- Parâmetros utilizados  
- Corretude e consistência  
- Qual TOOL o modelo escolhe quando existem múltiplas

---

## 📁 Estrutura do Repositório

```
src/
 └── main/
      ├── java/
      │    └── .../tools/
      │          ├── BankToolsA.java
      │          ├── BankToolsB.java
      │          ├── OperationType.java
      │
      └── resources/
           └── application.yaml
```

---

## 🛠️ Requisitos

- Java 17+
- Maven 3.8+
- Ollama instalado
- Modelo atribuído pelo professor (ex.: `llama3.2:latest`)
- Dependências LangChain4j

### Baixar o modelo no Ollama

```bash
ollama pull llama3.2:latest
```

---

## ▶️ Como Executar

### Rodar com Maven

```bash
mvn spring-boot:run
```

---

## ⚙️ Seleção das TOOLs (Spring Profiles)

### ✔️ Usar somente **BankToolsA**

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=toolsA
```

### ✔️ Usar somente **BankToolsB**  
⚠️ **Importante:** Para rodar a *ToolsB*, é obrigatório definir o profile no `application.yaml`.

No `application.yaml`:

```yaml
spring:
  profiles:
    active: toolsB
```
