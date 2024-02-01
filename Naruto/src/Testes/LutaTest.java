package Testes;

import Ninjas.Personagem;
import Operacoes.Luta;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testes que verificam o funcionamento da classe Luta
 */
class LutaTest {

    @Mock
    Luta luta = mock(Luta.class);
    @Test
    void regeAdversarios() {
        List<String> jutsus = new ArrayList<>();
        List<Personagem> personagens = new ArrayList<>();
        jutsus.add("1");
        jutsus.add("1");
        Personagem personagem = new Personagem("nome", "idade","clan",
                jutsus,1000,2,2);
        personagens.add(personagem);
        luta.regeAdversarios(personagens);
        verify(luta,times(1)).regeAdversarios(personagens);
    }
}