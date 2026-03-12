package com.dorisnunes;

public class Usuario implements Observador {
    private String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(String video) {
        System.out.println(nome + " foi notificado: Novo vídeo publicado - " + video);
    }
}
