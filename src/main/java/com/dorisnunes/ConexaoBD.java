package com.dorisnunes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/observeryoutube";
    private static final String URL_PADRAO = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String SENHA = "1234";
    private static Connection conexao;

    public static Connection getConexao() {
        if (conexao == null) {
            try {
                // Carregar driver PostgreSQL
                Class.forName("org.postgresql.Driver");
                
                // Tentar criar o banco de dados se não existir
                criarBancoDados();
                
                // Conectar ao banco de dados
                conexao = DriverManager.getConnection(URL, USER, SENHA);
                
                // Criar tabelas se não existirem
                criarTabelas();
                System.out.println(" Conexão com PostgreSQL estabelecida\n");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(" Erro ao conectar ao banco de dados: " + e.getMessage());
                System.out.println("Certifique-se de que o PostgreSQL está rodando em localhost:5432");
            }
        }
        return conexao;
    }

    private static void criarBancoDados() {
        try (Connection connPadrao = DriverManager.getConnection(URL_PADRAO, USER, SENHA);
            Statement stmt = connPadrao.createStatement()) {
            // Verificar se banco existe, se não, criar
            stmt.executeUpdate("CREATE DATABASE observeryoutube");
            System.out.println(" Banco de dados 'observeryoutube' criado");
        } catch (SQLException e) {
            // Se der erro 42P04 é porque o banco já existe, o que é ok
            if (e.getSQLState() != null && e.getSQLState().equals("42P04")) {
                System.out.println(" Banco de dados 'observeryoutube' já existe");
            } else {
                System.out.println(" Nota: " + e.getMessage());
            }
        }
    }

    private static void criarTabelas() {
        try (Statement stmt = conexao.createStatement()) {
            // Criar tabela usuarios
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id SERIAL PRIMARY KEY," +
                    "nome VARCHAR(100) NOT NULL UNIQUE," +
                    "data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
            System.out.println(" Tabela 'usuarios' criada/verificada");
            
            // Criar tabela inscricoes
            stmt.execute("CREATE TABLE IF NOT EXISTS inscricoes (" +
                    "id SERIAL PRIMARY KEY," +
                    "nome_usuario VARCHAR(100) NOT NULL," +
                    "nome_canal VARCHAR(100) NOT NULL," +
                    "data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (nome_usuario) REFERENCES usuarios(nome)" +
                    ")");
            System.out.println(" Tabela 'inscricoes' criada/verificada");

            // Criar tabela videos
            stmt.execute("CREATE TABLE IF NOT EXISTS videos (" +
                    "id SERIAL PRIMARY KEY," +
                    "nome_canal VARCHAR(100) NOT NULL," +
                    "titulo VARCHAR(255) NOT NULL," +
                    "data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
            System.out.println(" Tabela 'videos' criada/verificada\n");
        } catch (SQLException e) {
            System.out.println(" Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println(" Conexão com banco de dados fechada");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
