package ScreenMatchPKG.Modelos;

import ScreenMatchPKG.Calculos.Classificavel;

import java.util.Collections;

public class Filme extends Titulo implements Classificavel {
    private String diretor;

    public Filme(String nome, int ano) {
        super(nome, ano);
    }


    public String getDiretor() {
        return diretor;
    }
    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    @Override
    public int getClassificacao() {

        return (int) calculaMedia()/2;
    }

    @Override
    public String toString() {
        return "Filme: " + this.getNome() + " (" + this.getAno() + ")";
    }


}
