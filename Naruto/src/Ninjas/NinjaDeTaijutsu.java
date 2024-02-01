package Ninjas;

import Operacoes.GeradorDeImagens;

import java.util.Scanner;

/**
 * Classe filha da superclass Personagem. Caracteriza os ninjas mestres do taijutso
 * com suas reescritas específicas dos métodos da classe herdada.
 * @author mateus.scheuermann
 * @version 1.0.0
 */
public class NinjaDeTaijutsu  extends Personagem implements Combatente {
    public NinjaDeTaijutsu() {
    }

    public NinjaDeTaijutsu(Personagem personagem) {
        super(personagem.getName(), personagem.getIdade(), personagem.getClan(),
                personagem.getJutsus(), personagem.getChakra(), personagem.getVida(), personagem.getDesvios());
    }

    /**
     * Reescrita própria dos Ninjas de Taijutsu da função "usarjutsu" herdada da classe mãe "personagem",
     * de forma que o texto e as imagens que aparecem para o usuário ao realizar o ataque sejam
     * representações características de um ninja com especialidade em taijutsu.
     * Este método chama a função "gastaChakra" da classe mãe para reduzir o valor do chakra do personagem cada
     * vez que um jutsu é utilizado, verificando a presença do jutsu escolhido no array de Strings, o qual contém todos
     * os jutsus do ninja, e enviando o número da posição deste jutsu dentro do array para o método mencionado. Caso a
     * função "getChakra" retornar o valor de true, significa que o ninja ainda possuia chakra e o jutsu foi utilizado
     * com sucesso. Após o retorno do valor "true", o método "recebeDano" é chamado e também envia o parâmetro contendo
     * a posição da habilidade escolhida para reduzir a vida do ninja adversário alvo. Caso o retorno
     * for de valor "false", o usuário do jutsu não conseguirá atacar e uma mensagem avisará que o mesmo está sem chakra.
     * @param personagemInimigo é o ninja inimigo do ninja que está executando o jutsu e que receberá dano a partir da função
     *"recebeDano".
     *
     */
    @Override
    public void usarJutsu(Personagem personagemInimigo) {
        String escolhaJutsu = "";
        boolean usouJutso = false;
        int posicaoJutsu;
        GeradorDeImagens gifs = new GeradorDeImagens();
        Scanner teclado = new Scanner(System.in);
        while(usouJutso == false) {
            System.out.println("O ninja " + getName() + " prepara os seus golpes para atingir o adversário" +
                    " e, em seguida, escolhe seu jutsu!");
            gifs.mostraGif("https://pa1.aminoapps.com/6575/4db926b205e5d8b5aeaeebe17443c49083d65dce_hq.gif");
            System.out.println("Qual jutsu você irá usar?\n" + getJutsus());
            escolhaJutsu = teclado.nextLine();
            posicaoJutsu = this.getJutsus().indexOf(escolhaJutsu);
            if (this.getJutsus().contains(escolhaJutsu)) {
                System.out.println(this.getJutsus().get(posicaoJutsu));
                if(this.gastaChakra(posicaoJutsu + 1)){
                    if((posicaoJutsu + 1) <= 2) {
                        gifs.mostraGif("https://media.tenor.com/EX9xJ2KthjMAAAAC/rock-lee-gaara.gif");
                    } else if (2 < (posicaoJutsu + 1) && (posicaoJutsu + 1) <= 4) {
                        gifs.mostraGif("https://i.pinimg.com/originals/6c/57/5f/6c575f7cc514d20f44d4d0b858dc2e93.gif");
                    } else if ((posicaoJutsu + 1) > 4) {
                        gifs.mostraGif("https://pa1.aminoapps.com/6580/41a5e5049f30c1e613466d1828e0f471bd78860e_hq.gif");
                    }
                    personagemInimigo.recebeDano(getJutsus().indexOf(escolhaJutsu)+1);
                    System.out.println("O jutsu foi utilizado!" +
                            "\no seu chakra restante é:" + getChakra());
                }
                else{
                    System.out.println("Você não possui chakra suficiente!");
                }
                usouJutso = true;
            } else {
                System.out.println("O ninja não possui este jutsu, tente novamente");
            }
        }
    }

    /**
     * Reescrita própria dos Ninjas de Taijutsu da função "desviar" herdada da classe mãe "personagem",
     * de forma que o texto e as imagens que aparecem para o usuário ao realizar um desvio sejam
     * representações características de um minja com especialidade em taijutsu.
     */
    @Override
    public void desviar() {
        GeradorDeImagens gifs = new GeradorDeImagens();
        System.out.println("O ninja " + getName() + " faz diferentes acrobacias inspiradas em taijutsu" +
                " para evitar o dano do ataque do oponente!");
        gifs.mostraGif("https://i.pinimg.com/originals/d4/ee/1a/d4ee1a76c0615b12c5bc34c7a225009e.gif");
        this.setDesviouUltimoTurno(true);
        this.gastaDesvio();
    }
}
