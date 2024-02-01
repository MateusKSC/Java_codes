package Testes;

import Operacoes.GeradorDeImagens;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testes que verificam o funcionamento da classe GeradorDeImagens
 */
class GeradorDeImagensTest {
    @Mock
    GeradorDeImagens imagens = mock(GeradorDeImagens.class);

    @Test
    void mostraGif() {
        //Testando input válido
        imagens.mostraGif("https://i.pinimg.com/originals/bc/25/24/bc252472462ca689922581abfb7a9310.gif");
        verify(imagens,times(1)).mostraGif("https://i.pinimg." +
                "com/originals/bc/25/24/bc252472462ca689922581abfb7a9310.gif");

        //Testando input inválido
        imagens.mostraGif("htt://i.pinimg.c/bc/25/24/bc252472462ca689922581abfb7a9310.gif");
        verify(imagens,times(1)).mostraGif("htt://i.pinimg.c/bc/25" +
                "/24/bc252472462ca689922581abfb7a9310.gif");
    }
    @Test
    void mostraRetratos() {
        //Testando input válido;
        imagens.mostraRetratos("https://static.wikia.nocookie.net/naruto/images/d/d6/Naruto_Part_I.png");
        verify(imagens, times(1)).mostraRetratos("https://static.wikia." +
                "nocookie.net/naruto/images/d/d6/Naruto_Part_I.png");

        //Testando input inválido;
        imagens.mostraRetratos("hps://static.wikia.nocoet/naruto/images/d/d6/Naru_Part_I.png");
        verify(imagens, times(1)).mostraRetratos("hps://static.wikia.n" +
                "ocoet/naruto/images/d/d6/Naru_Part_I.png");
    }
}