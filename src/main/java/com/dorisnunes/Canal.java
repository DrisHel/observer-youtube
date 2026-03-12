package com.dorisnunes;

import java.util.ArrayList;
import java.util.List;

public class Canal {
    private String nome;
    private List<Observador> inscritos = new ArrayList<>();

    public Canal(String nome) {
        this.nome = nome;
    }

    public void inscrever(Observador observador) {
        inscritos.add(observador);
        System.out.println(observador + " se inscreveu no canal " + nome);
    }

    public void publicarVideo(String titulo) {
        System.out.println("Canal " + nome + " publicou: " + titulo);
        notificarInscritos(titulo);
    }

    private void notificarInscritos(String video) {
        for (Observador observador : inscritos) {
            observador.atualizar(video);
        }
    }
}
