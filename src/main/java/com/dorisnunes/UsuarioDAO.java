package com.dorisnunes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static void salvar(String nome) {
        String sql = "INSERT INTO usuarios (nome) VALUES (?)";
        try {
            Connection conexao = ConexaoBD.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("\n Usuário '" + nome + "' salvo no banco de dados");
        } catch (SQLException e) {
            System.out.println(" Erro ao salvar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void salvarInscricao(String nomeUsuario, String nomeCanal) {
        String sql = "INSERT INTO inscricoes (nome_usuario, nome_canal) VALUES (?, ?)";
        try {
            Connection conexao = ConexaoBD.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nomeUsuario);
            pstmt.setString(2, nomeCanal);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println(" Inscrição salva no banco de dados");
        } catch (SQLException e) {
            System.out.println(" Erro ao salvar inscrição: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<String> listarTodos() {
        List<String> usuarios = new ArrayList<>();
        String sql = "SELECT nome FROM usuarios ORDER BY data_inscricao DESC";
        try {
            Connection conexao = ConexaoBD.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                usuarios.add(rs.getString("nome"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(" Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    public static List<String> listarInscritos(String nomeCanal) {
        List<String> inscritos = new ArrayList<>();
        String sql = "SELECT DISTINCT nome_usuario FROM inscricoes WHERE nome_canal = ? ORDER BY data_inscricao DESC";
        try {
            Connection conexao = ConexaoBD.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nomeCanal);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                inscritos.add(rs.getString("nome_usuario"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(" Erro ao listar inscritos: " + e.getMessage());
            e.printStackTrace();
        }
        return inscritos;
    }

    public static int contarInscritos(String nomeCanal) {
        String sql = "SELECT COUNT(DISTINCT nome_usuario) as total FROM inscricoes WHERE nome_canal = ?";
        try {
            Connection conexao = ConexaoBD.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nomeCanal);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                rs.close();
                pstmt.close();
                return total;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(" Erro ao contar inscritos: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean existe(String nome) {
        String sql = "SELECT 1 FROM usuarios WHERE nome = ?";
        try {
            Connection conexao = ConexaoBD.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();
            boolean existe = rs.next();
            rs.close();
            pstmt.close();
            return existe;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

