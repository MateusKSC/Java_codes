package Testes;

import Ninjas.NinjaDeGenjutsu;
import Ninjas.NinjaDeNinjutsu;
import Ninjas.NinjaDeTaijutsu;
import Ninjas.Personagem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Classe de testes que verificam o funcionamento da classe Personagem
 */
class PersonagemTest {
    @Mock
    NinjaDeNinjutsu ninjaNinjutsu = mock(NinjaDeNinjutsu.class);
    @Mock
    NinjaDeGenjutsu ninjaGenjutsu = mock(NinjaDeGenjutsu.class);
    @Mock
    NinjaDeTaijutsu ninjaTaijutsu = mock(NinjaDeTaijutsu.class);

    @Test
    void gastaChakra() {
        boolean chakraVazio = false;
        Personagem personagem1 = new Personagem();
        Personagem personagem2 = new Personagem();

        //Verificando o retorno com valor "False" no momento que a variável Chakra chega a 0.
        for (int i = 0; i < 2; i++) {
            chakraVazio = personagem1.gastaChakra(5);
        }
        Assertions.assertTrue(chakraVazio);

        //Verificando o retorno com valor "False" no momento que a variável Chakra chega a 0.
        for (int i = 0; i < 3; i++) {
            chakraVazio = personagem2.gastaChakra(5);
        }
        Assertions.assertFalse(chakraVazio);
    }

    @Test
    void recebeDano() {
        boolean vidaVazia = false;
        Personagem personagem1 = new Personagem();
        Personagem personagem2 = new Personagem();

        //Verificando o retorno com valor "False" no momento que a variável Vida chega a 0.
        for (int i = 0; i < 2; i++) {
            vidaVazia = personagem1.recebeDano(3);
        }
        Assertions.assertTrue(vidaVazia);

        //Verificando o retorno com valor "False" no momento que a variável Chakra chega a 0.
        for (int i = 0; i < 3; i++) {
            vidaVazia = personagem2.recebeDano(3);
        }
        Assertions.assertFalse(vidaVazia);
    }


    @Test
    void usarjutsu() {

        Personagem personagem = new Personagem();
        List<String> jutsus = new ArrayList<>();

        ninjaNinjutsu.setChakra(0);
        ninjaGenjutsu.setChakra(0);
        ninjaTaijutsu.setChakra(0);
        jutsus.add("1");
        jutsus.add("2");
        ninjaNinjutsu.setJutsus(jutsus);
        ninjaGenjutsu.setJutsus(jutsus);
        ninjaTaijutsu.setJutsus(jutsus);

        ByteArrayInputStream testIn = new ByteArrayInputStream("1".getBytes());
        System.setIn(testIn);
        ninjaNinjutsu.usarJutsu(personagem);
        verify(ninjaNinjutsu,times(1)).usarJutsu(personagem);

        System.setIn(testIn);
        ninjaGenjutsu.usarJutsu(personagem);
        verify(ninjaGenjutsu,times(1)).usarJutsu(personagem);

        System.setIn(testIn);
        ninjaTaijutsu.usarJutsu(personagem);
        verify(ninjaTaijutsu,times(1)).usarJutsu(personagem);

    }

    @Test
    void desviar() {
    }
}