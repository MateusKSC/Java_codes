package Operacoes;

import Ninjas.Personagem;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por reger as operações de ataque e defesa durante a luta dos ninjas.
 * @author mateus.scheuermann
 */
public class Luta {
    /**
     * Principal método responsável por apresentar e reger todas as ações do combate após os dois ninjas
     * adversários terem sido escolhidos pelo jogador.
     * Nesta função o jogador escolhe se quer usar o jutso ou desviar e recebe notificações sobre as consequências
     * de cada escolha e informações sobre quantidade de vida e chakra remanescentes. Além disso, é feita a limitação
     * da quantidade de desvios possíveis para três.
     * @param personagens é a lista contendo os dois personagens escolhidos pelo jogador na função main.
     */
    public void regeAdversarios(List<Personagem> personagens) {
        int escolhaAcao = 0;
        int vezNinja = 0;
        String vencedor = "";

        Scanner teclado = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            System.out.println("-----------------------------------------");
        }
        System.out.println("O combate irá começar!!! Os adversários são:");
        System.out.println(personagens.get(0));
        System.out.println(personagens.get(1));
        System.out.println("""
                
                ****************************************************************************
                Dica: Conforme o número da posição do jutsu na lista de jutsus aumenta,
                mais chakra ele custa e mais dano a habilidade causa ao adversário!
                ****************************************************************************
                
                """);
        while ((personagens.get(0).getVida() > 0) && (personagens.get(1).getVida() > 0)) {
            System.out.println("\nÉ a vez de " + personagens.get(vezNinja).getName() + "!\n" +
                    "Vida: " + personagens.get(vezNinja).getVida() + "          " +
                    "Chakra: " + personagens.get(vezNinja).getChakra() + "\n");
            System.out.println("""
                    Qual será a sua próxima ação?\n
                    1: Usar jutsu: Você ataca o seu oponente causando dano e
                    gastando chakra.
                    2: Desviar: Você desviará do ataque desferido pelo seu oponente
                    no próximo golpe que ele desferir, mas cuidado! Você só pode desviar 3 vezes. 
                    """);
            escolhaAcao = teclado.nextInt();
            if (escolhaAcao == 1) {
                if (vezNinja == 0) {
                    if (!(personagens.get(1).isDesviouUltimoTurno())) {
                        personagens.get(vezNinja).usarJutsu(personagens.get(1));
                        System.out.println("O adversário " + personagens.get(1).getName() +
                                " recebeu dano! A sua vida atual é " +
                                personagens.get(1).getVida());
                    } else {
                        System.out.println("O personagem adversário " + personagens.get(1).getName() +
                                " desviou do último golpe realizado! Ele possui só mais " +
                                personagens.get(1).getDesvios() + " desvios");
                        personagens.get(1).setDesviouUltimoTurno(false);
                    }
                } else if (vezNinja == 1) {
                    if (!(personagens.get(0).isDesviouUltimoTurno())) {
                        personagens.get(vezNinja).usarJutsu(personagens.get(0));
                        System.out.println("O adversário " + personagens.get(0).getName() +
                                " recebeu dano! A sua vida atual é: " +
                                personagens.get(0).getVida());
                    } else {
                        System.out.println("O personagem adversário " + personagens.get(0).getName() +
                                " desviou do último golpe realizado! Ele possui só mais " +
                                personagens.get(0).getDesvios() + " desvios");
                        personagens.get(0).setDesviouUltimoTurno(false);
                    }
                }
            } else if (escolhaAcao == 2) {
                personagens.get(vezNinja).desviar();
            }
            if(vezNinja == 0){
                vezNinja = 1;
            } else if (vezNinja == 1) {
                vezNinja = 0;
            }
        }
        if((personagens.get(0).getVida() == 0)){
            vencedor = personagens.get(1).getName();
        } else if ((personagens.get(1).getVida() == 0)) {
            vencedor = personagens.get(0).getName();
        }
        System.out.println("O vencedor da batalha é " + vencedor + "!");
    }
}


