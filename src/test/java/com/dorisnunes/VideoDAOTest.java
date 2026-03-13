package com.dorisnunes;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class VideoDAOTest {

    private static final String CANAL = "DevJava";

    @Before
    public void setup() throws SQLException {
        // Garantir que temos uma base limpa para os testes
        Connection conexao = ConexaoBD.getConexao();
        try (PreparedStatement pstmt = conexao.prepareStatement("DELETE FROM videos WHERE nome_canal = ?")) {
            pstmt.setString(1, CANAL);
            pstmt.executeUpdate();
        }
    }

    @Test
    public void deveContarVideosDepoisDeSalvar() {
        VideoDAO.salvar(CANAL, "PHP para iniciantes.");
        VideoDAO.salvar(CANAL, "Java para iniciantes.");

        int total = VideoDAO.contarVideos(CANAL);
        assertEquals(2, total);
    }

    @Test
    public void deveListarTitulosEmOrdemDecrescenteDePublicacao() throws SQLException {
        // Inserir vídeos com timestamps controlados para garantir a ordem esperada
        inserirVideoComTimestamp(CANAL, "Vídeo mais antigo", "2020-01-01T12:00:00");
        inserirVideoComTimestamp(CANAL, "Vídeo mais recente", "2020-01-01T12:01:00");

        List<String> titulos = VideoDAO.listarTitulos(CANAL);
        assertEquals(2, titulos.size());
        assertEquals("Vídeo mais recente", titulos.get(0));
        assertEquals("Vídeo mais antigo", titulos.get(1));
    }

    private void inserirVideoComTimestamp(String canal, String titulo, String timestamp) throws SQLException {
        String sql = "INSERT INTO videos (nome_canal, titulo, data_publicacao) VALUES (?, ?, ?)";
        Connection conexao = ConexaoBD.getConexao();
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, canal);
            pstmt.setString(2, titulo);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.parse(timestamp)));
            pstmt.executeUpdate();
        }
    }
}
