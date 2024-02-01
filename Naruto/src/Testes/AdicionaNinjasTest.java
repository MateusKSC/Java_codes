package Testes;

import Operacoes.AdicionaNinjas;
import org.junit.jupiter.api.Test;
import Operacoes.GeradorDeImagens;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de testes que verificam o funcionamento da classe GeradorDeImagens
 */
class AdicionaNinjasTest {
    @Mock
    AdicionaNinjas novosNinjas = mock(AdicionaNinjas.class);


    @Test
    void registraNinjas() {
        novosNinjas.registraNinjas();
        doNothing().when(novosNinjas).registraNinjas();
        verify(novosNinjas,times(1)).registraNinjas();
    }
}