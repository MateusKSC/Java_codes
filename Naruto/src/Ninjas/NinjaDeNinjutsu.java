package Ninjas;

import Operacoes.GeradorDeImagens;

import java.util.List;
import java.util.Scanner;

/**
 * Classe filha da superclass Personagem. Caracteriza os ninjas mestres do ninjutso
 * com suas reescritas específicas dos métodos da classe herdada.
 * @author mateus.scheuermann
 * @version 1.0.0
 */
public class NinjaDeNinjutsu extends Personagem implements Combatente {
    public NinjaDeNinjutsu() {
    }

    public NinjaDeNinjutsu(Personagem personagem) {
        super(personagem.getName(), personagem.getIdade(), personagem.getClan(),
                personagem.getJutsus(), personagem.getChakra(), personagem.getVida(), personagem.getDesvios());
    }


    /**
     * Reescrita própria dos Ninjas de Ninjutsu da função "usarjutsu" herdada da classe mãe "personagem",
     * de forma que o texto e as imagens que aparecem para o usuário ao realizar o ataque sejam
     * representações características de um ninja com especialidade em ninjutsu.
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
        int posicaoJutsu = 0;
        boolean usouJutso = false;
        GeradorDeImagens gifs = new GeradorDeImagens();
        Scanner teclado = new Scanner(System.in);
        while(usouJutso == false) {
            System.out.println("O ninja " + getName() + " prepara os seus selos com rapidez" +
                    " e, em seguida, escolhe seu jutsu!");
            gifs.mostraGif("https://imgur.com/23J13Iw.gif");
            System.out.println("Qual jutsu você irá usar? Digite o nome " +
                    "a partir da lista informada\n" + getJutsus());
            escolhaJutsu = teclado.nextLine();
            posicaoJutsu = this.getJutsus().indexOf(escolhaJutsu);
            if (this.getJutsus().contains(escolhaJutsu)) {
                System.out.println(this.getJutsus().get(posicaoJutsu));
                if(this.gastaChakra(posicaoJutsu + 1)){
                    if((posicaoJutsu + 1) <= 2) {
                        gifs.mostraGif("https://pa1.aminoapps.com/6284/9a837eb6f3c25328ef59ee8ac38a1512b7075354_hq.gif");
                    } else if (2 < (posicaoJutsu + 1) && (posicaoJutsu + 1) <= 4) {
                        gifs.mostraGif("https://i.pinimg.com/originals/bc/25/24/bc252472462ca689922581abfb7a9310.gif");
                    } else if ((posicaoJutsu + 1) > 4) {
                        gifs.mostraGif("https://i.pinimg.com/originals/85/7e/84/857e84d311aa364e8fcc46bd08a9a5b5.gif");
                    }
                    personagemInimigo.recebeDano(getJutsus().indexOf(escolhaJutsu)+1);
                    System.out.println("O jutsu foi utilizado!" +
                            "\nO seu chakra restante é de: " + getChakra());
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
     * Reescrita própria dos Ninjas de Ninjutsu da função "desviar" herdada da classe mãe "personagem",
     * de forma que o texto e as imagens que aparecem para o usuário ao realizar um desvio sejam
     * representações características de um minja com especialidade em ninjutsu.
     */
    @Override
    public void desviar() {
        GeradorDeImagens gifs = new GeradorDeImagens();
        System.out.println("O ninja " + getName() + " se esforça para realizar manobras evasivas para" +
                " desviar do ataque do adversário!");
        gifs.mostraGif("https://gifdb.com/images/high/naruto-run-dodging-shuriken-goofy-funny-" +
                "face-zzi16b9s7hlq8irr.gif");
        this.setDesviouUltimoTurno(true);
        this.gastaDesvio();
    }

    @Override
    public boolean recebeDano(int jutsu) {
        return super.recebeDano(jutsu);
    }
}
