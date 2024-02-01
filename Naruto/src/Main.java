
import Operacoes.*;

/**
 * Jogo de Duelos do Naruto
 *
 * Escolha dois ninjas, especifique para cada um deles a sua especialidade em termos
 * das artes do ninjutsu, genjutsu e taijutsu e faça os dois duelarem, escolhendo
 * estrategicamente entre atacar e se defender. O ninja vencedor será o que fazer o
 * adversário chegar a 0 pontos de vida primeiro.
 *
 * Para fazer a escolha dos ninjas, consulte o nome exato do que você deseja escolher
 * a partir do link do catálogo da API Naruto DB a seguir:
 * "https://narutodb.xyz/api/character?page=1&limit=2000"
 * Os nomes inseridos não são case sensitive, mas precisam utilizar a mesma acentuação
 * dos que estão presentes no banco de dados acessível no link informado acima.
 *
 * @author mateus.scheuermann
 * @date 02/2024
 */
public class Main {
    public static void main(String[] args) {
        AdicionaNinjas novosNinjas = new AdicionaNinjas();
        novosNinjas.registraNinjas();
    }
}