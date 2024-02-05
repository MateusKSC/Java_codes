package ScreenMatchPKG.Calculos;

import ScreenMatchPKG.Modelos.Filme;
public class TempoTotal {
    private int tempoTotal;

    public int getTempototal() {
        return tempoTotal;
    }

    public void inclui  (Filme f){
        tempoTotal += f.getTempo();
    }

}
