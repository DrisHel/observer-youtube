package com.dorisnunes;

import java.util.ArrayList;
import java.util.List;

public class Canal {
    private String nome;
    private List<Observador> inscritos = new ArrayList<>();

    public Canal(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void inscrever(Observador observador) {
        inscritos.add(observador);
        System.out.println(" Usuário se inscreveu no canal " + nome);
    }

    public void publicarVideo(String titulo) {
        System.out.println("\n Canal " + nome + " publicou: \"" + titulo + "\"");

        // Persistir informação de vídeo publicado (para estatísticas)
        VideoDAO.salvar(nome, titulo);

        notificarInscritos(titulo);
    }

    private void notificarInscritos(String video) {
        if (inscritos.isEmpty()) {
            System.out.println("(Nenhum inscritor para notificar)");
        } else {
            for (Observador observador : inscritos) {
                observador.atualizar(video);
            }
        }
    }
}
