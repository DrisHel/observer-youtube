# Observer YouTube (Padrão Observer)

## 📌 Finalidade do projeto
Este projeto é uma implementação do **padrão de projeto Observer (Observador)** em Java.

A aplicação simula um **canal** (`Canal`) ao qual **usuários** (`Usuario`) podem se inscrever para receber notificações quando um novo vídeo é publicado. A ideia é demonstrar como um objeto (o canal) notifica seus observadores (usuários) quando seu estado muda.

---

## 🧰 Tecnologias e dependências utilizadas
- **Java 11** (definido em `pom.xml`)
- **Maven** (build tool / gerenciamento de dependências)
- **PostgreSQL** (persistência de dados)

### Dependências chave (via `pom.xml`)
- `junit:junit:4.11` (para testes)
- `org.postgresql:postgresql:42.7.1` (driver JDBC para PostgreSQL)

---

## ⚙️ Como configurar e rodar
### 1) Pré-requisitos (Windows e Linux)
- **Java JDK 11+** instalado
- **Maven** instalado
- **PostgreSQL** rodando localmente em `localhost:5432`

> **Importante:** A aplicação usa credenciais fixas em `ConexaoBD.java` (usuário `postgres`, senha `1234`). Se você usa outras credenciais, atualize `ConexaoBD.java` antes de rodar.

### 2) Configurar o banco de dados
A aplicação tenta criar automaticamente o banco de dados `observeryoutube` e as tabelas necessárias (`usuarios`, `inscricoes`) na primeira execução.

Se você quiser criar o banco manualmente, use (exemplo):
```sql
CREATE DATABASE observeryoutube;
```

### 3) Build + execução
Abra um terminal (cmd/powershell no Windows, bash no Linux) e vá até a raiz do projeto: Exemplo ...
```bash
cd "c:\Users\Doris\Documents\Doris\IFRS - 2026\1 Semestre\PRINCÍPIOS E PADRÕES DE PROJETO\Trabalho 2 - Observer\observer-youtube"
```

#### ✅ Compilar o projeto
```bash
mvn clean compile
```

#### ✅ Executar a aplicação
A classe principal é `com.dorisnunes.App`, que inicia um menu interativo no console com as seguintes opções:
- 1. Cadastrar novo usuário
- 2. Inscrever usuário no canal
- 3. Publicar vídeo
- 4. Ver estatísticas (total de usuários, inscritos, vídeos e lista dos últimos vídeos publicados)
- 5. Sair

##### Opção 1: executar direto com Maven (sem gerar JAR)
```bash
mvn -q exec:java -Dexec.mainClass="com.dorisnunes.App"
```

##### Opção 2: gerar JAR e executar
```bash
mvn clean package
java -jar target/observer-youtube-*.jar
```

---

## 🧪 Testes
Para rodar a suíte de testes:
```bash
mvn test
```

---

## 🔎 Nota sobre configuração de banco de dados
Se o PostgreSQL não estiver rodando ou as credenciais estiverem incorretas, a aplicação exibirá uma mensagem indicando que não conseguiu conectar em `localhost:5432`. Ajuste `ConexaoBD.java` ou as configurações do PostgreSQL conforme necessário.
