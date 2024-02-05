package ScreenMatchPKG.Principal;

import ScreenMatchPKG.Modelos.Filme;
import ScreenMatchPKG.Modelos.Serie;
import ScreenMatchPKG.Modelos.Titulo;

import java.util.*;

public class PrincipalComListas {
    public static void main(String[] args) {
        Filme filme1 = new Filme("topperson", 1980);
        Filme filme2 = new Filme("topperson2", 2002);
        Filme filme3 = new Filme("topperson3", 2003);
        Serie serie1 = new Serie("doctor who", 1960);

        filme1.avalia(7);
        filme2.avalia(9);
        filme3.avalia(10);
        List<Titulo> lista = new LinkedList<>();
        lista.add(filme1);
        lista.add(filme2);
        lista.add(filme3);
        lista.add(serie1);

        for (Titulo titulo : lista) {
            System.out.println(titulo.getNome());
            if (titulo instanceof Filme filme && filme.getClassificacao() > 2){
                System.out.println("Classificacao: " + filme.getClassificacao());
            }
        }
        ArrayList nomes = new ArrayList();
        nomes.add("adam");
        nomes.add("wsandler");
        nomes.add("uerp");
        lista.sort(Comparator.comparing(Titulo::getAno));
        System.out.println(lista);



    }
}
