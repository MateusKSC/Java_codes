package ScreenMatchPKG.Modelos;

import ScreenMatchPKG.Excecao.YearConvertionException;
import com.google.gson.annotations.SerializedName;

public class Titulo implements Comparable<Titulo> {
    private String nome;

    private int ano;
    private boolean noPlano;
    private double avaliacao;
    private int numNotas;
    private double somaNotas;
    private int tempo;

    public Titulo(String nome, int ano) {
        this.nome = nome;
        this.ano = ano;
    }
    public Titulo(TituloOmdb meuTituloOmdb) {
        this.nome = meuTituloOmdb.title();
        if(meuTituloOmdb.year().length() > 4){
            throw new YearConvertionException("The specified year value had " +
                    "a length bigger than 4 characters.");
        }
        this.ano = Integer.valueOf(meuTituloOmdb.year());
        this.tempo = Integer.valueOf(meuTituloOmdb.runtime().substring(0,2));

    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public void setNoPlano(boolean noPlano) {
        this.noPlano = noPlano;
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public int getTempo() {
        return tempo;
    }
    public int getNumNotas(){
        return numNotas;
    }
    public void descreveFicha(){
        System.out.println(nome + " " + ano);

    }
    public void avalia(double nota){
        somaNotas += nota;
        numNotas++;
    }

    @Override
    public int compareTo(Titulo outroTitulo) {
        return this.getNome().compareTo(outroTitulo.getNome());
    }

    @Override
    public String toString() {
        return "{" +
                "nome='" + nome + '\'' +
                ", ano=" + ano +
                ", tempo=" + tempo +
                '}';
    }

    public double calculaMedia(){
        return somaNotas/numNotas;
    }
}
