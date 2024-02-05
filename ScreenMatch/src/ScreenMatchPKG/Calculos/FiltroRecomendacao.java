package ScreenMatchPKG.Calculos;

public class FiltroRecomendacao {
    private String recomendacao;

    public void filtra(Classificavel classificavel){
        if (classificavel.getClassificacao() >= 4){
            System.out.println("É um dos preferidos!");
        } else if (classificavel.getClassificacao() >= 2) {
            System.out.println("Bem avaliado!");
        } else {
            System.out.println("Não vale a pena!");
        }

    }
}
