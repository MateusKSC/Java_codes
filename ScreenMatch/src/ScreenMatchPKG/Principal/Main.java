package ScreenMatchPKG.Principal;

import ScreenMatchPKG.Modelos.*;
import ScreenMatchPKG.Calculos.*;

import java.util.ArrayList;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Filme filme1 = new Filme("topperson", 1);
        Filme filme2 = new Filme("topperson2", 2);
        Filme filme3 = new Filme("topperson3", 3);
        Serie serie1 = new Serie("doctor who", 1960);
        List <Titulo> titulos= new ArrayList<Titulo>();
        TempoTotal tempo1 = new TempoTotal();
        Episodio episodio = new Episodio();
        FiltroRecomendacao filtro = new FiltroRecomendacao();

        filme1.setTempo(150);
        filme1.setNoPlano(true);
        filme1.descreveFicha();
        titulos.add(filme1);
        filme1.avalia(10);
        filme1.avalia(10);
        filme1.avalia(5);

        System.out.println(filme1.getTempo());
        filtro.filtra(filme1);
        serie1.descreveFicha();
        serie1.setTemporadas(14);
        serie1.setTempo(45);
        serie1.setEpisodiosPorTemporada(10);

        episodio.setNumero(1);
        episodio.setSerie(serie1);
        episodio.setTotalVisualizacoes(500);
        filtro.filtra(episodio);

        tempo1.inclui(filme1);

        System.out.println(tempo1.getTempototal());

        ArrayList<Filme> listaDeFilmes = new ArrayList<>();
        listaDeFilmes.add(filme1);
        listaDeFilmes.add(filme2);
        listaDeFilmes.add(filme3);
        System.out.println(listaDeFilmes.size());
        System.out.println(listaDeFilmes.get(0).getNome());
        System.out.println(listaDeFilmes.toString());


    }

}
