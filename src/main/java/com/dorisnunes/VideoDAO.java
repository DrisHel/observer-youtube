package com.dorisnunes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoDAO {

    public static void salvar(String nomeCanal, String titulo) {
        String sql = "INSERT INTO videos (nome_canal, titulo) VALUES (?, ?)";
        try {
            Connection conexao = ConexaoBD.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nomeCanal);
            pstmt.setString(2, titulo);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println(" Vídeo salvo no banco de dados");
        } catch (SQLException e) {
            System.out.println(" Erro ao salvar vídeo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int contarVideos(String nomeCanal) {
        String sql = "SELECT COUNT(*) AS total FROM videos WHERE nome_canal = ?";
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
            System.out.println("⚠ Erro ao contar vídeos: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
