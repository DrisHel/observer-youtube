package com.dorisnunes;

import java.util.Scanner;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Inicializar banco de dados
        ConexaoBD.getConexao();
        
        Canal canal = new Canal("DevJava");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n\n==== Bem vindo ao Canal DevJava!====\n\n");

        while (running) {
            System.out.println("            *** Menu:***\n");
            System.out.println("1. Cadastrar novo usuário");
            System.out.println("2. Inscrever usuário no canal");
            System.out.println("3. Publicar vídeo");
            System.out.println("4. Ver estatísticas");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    cadastrarUsuario(scanner);
                    break;
                case "2":
                    inscreverUsuario(scanner, canal);
                    break;
                case "3":
                    publicarVideo(scanner, canal);
                    break;
                case "4":
                    verEstatisticas();
                    break;
                case "5":
                    running = false;
                    System.out.println("\n Até logo!");
                    break;
                default:
                    System.out.println(" Opção inválida!\n");
            }
        }
        
        scanner.close();
        ConexaoBD.fecharConexao();
    }

    private static void cadastrarUsuario(Scanner scanner) {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine().trim();
        
        if (nome.isEmpty()) {
            System.out.println(" Nome não pode ser vazio.\n");
        } else if (UsuarioDAO.existe(nome)) {
            System.out.println(" Usuário já cadastrado.\n");
        } else {
            UsuarioDAO.salvar(nome);
            System.out.println(" \nUsuário '" + nome + "' cadastrado com sucesso!\n");
        }
    }

    private static void inscreverUsuario(Scanner scanner, Canal canal) {
        System.out.print("Digite o nome do usuário para inscrever: ");
        String nomeUsuario = scanner.nextLine().trim();
        
        if (!UsuarioDAO.existe(nomeUsuario)) {
            System.out.println(" \nUsuário não encontrado, cadastre primeiro.\n");
        } else {
            String nomeCanal = canal.getNome();
            
            // Salvar inscrição no banco
            UsuarioDAO.salvarInscricao(nomeUsuario, nomeCanal);
            
            // Notificar pelo padrão Observer
            Usuario usuario = new Usuario(nomeUsuario);
            canal.inscrever(usuario);
            
            System.out.println(" Inscrição realizada!\n");
        }
    }

    private static void publicarVideo(Scanner scanner, Canal canal) {
        System.out.print("Digite o título do vídeo: ");
        String titulo = scanner.nextLine().trim();
        
        if (!titulo.isEmpty()) {
            canal.publicarVideo(titulo);
            System.out.println(" Vídeo publicado!\n");
        } else {
            System.out.println(" Título não pode ser vazio.\n");
        }
    }

    private static void verEstatisticas() {
        int totalUsuarios = UsuarioDAO.listarTodos().size();
        int totalInscritos = UsuarioDAO.contarInscritos("DevJava");
        
        System.out.println("\n=== 📊 ESTATÍSTICAS DO CANAL ===");
        System.out.println("Total de usuários cadastrados: " + totalUsuarios);
        System.out.println("Total de inscritos no canal: " + totalInscritos);
        System.out.println("================================\n");
    }
}