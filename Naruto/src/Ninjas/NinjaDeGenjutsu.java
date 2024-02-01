package Ninjas;

import Operacoes.GeradorDeImagens;

import java.util.Scanner;

/**
 * Classe filha da superclass Personagem. Caracteriza os ninjas mestres do genjutso
 * com suas reescritas específicas dos métodos da classe herdada.
 * @author mateus.scheuermann
 * @version 1.0.0
 */
public class NinjaDeGenjutsu extends Personagem implements Combatente {

    public NinjaDeGenjutsu() {
    }

    public NinjaDeGenjutsu(Personagem personagem) {
        super(personagem.getName(), personagem.getIdade(), personagem.getClan(),
                personagem.getJutsus(), personagem.getChakra(), personagem.getVida(), personagem.getDesvios());
    }

    /**
     * Reescrita própria dos Ninjas de Genjutso da função "usarjutsu" herdada da classe mãe "personagem",
     * de forma que o texto e as imagens que aparecem para o usuário ao realizar o ataque sejam
     * representações características de um ninja com especialidade em genjutso.
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
            System.out.println("O ninja " + getName() + " prepara um genjutso para atacar " +
                    "o adversário! Rápido, escolha o seu jutsu!");
            gifs.mostraGif("https://pa1.aminoapps.com/6315/990a9abb5eca634554b76b3c0e3bfe235e00a7ff_hq.gif");
            System.out.println("Qual jutsu você irá usar?\n" + getJutsus());
            escolhaJutsu = teclado.nextLine();
            posicaoJutsu = this.getJutsus().indexOf(escolhaJutsu);
            if (this.getJutsus().contains(escolhaJutsu)) {
                System.out.println(this.getJutsus().get(posicaoJutsu));
                if(this.gastaChakra(posicaoJutsu + 1)){
                    if((posicaoJutsu + 1) <= 2) {
                        gifs.mostraGif("https://i.pinimg.com/originals/ac/de/3e/acde3e65d3c66576a49d4db629eb43b1.gif");
                    } else if (2 < (posicaoJutsu + 1) && (posicaoJutsu + 1) <= 4) {
                        gifs.mostraGif("https://i.pinimg.com/originals/64/ea/6b/64ea6b7a00237e3cfdcd5bb99e395dec.gif");
                    } else if ((posicaoJutsu + 1) > 4) {
                        gifs.mostraGif("https://qph.cf2.quoracdn.net/main-qimg-009346ac197d5bdfc285e3877adadb26");
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
     * Reescrita própria dos Ninjas de Genjutsu da função "desviar" herdada da classe mãe "personagem",
     * de forma que o texto e as imagens que aparecem para o usuário ao realizar um desvio sejam
     * representações características de um minja com especialidade em genjutsu.
     */
    @Override
    public void desviar() {
        GeradorDeImagens gifs = new GeradorDeImagens();
        System.out.println("O ninja " + getName() + " irá prender o inimigo em um genjutsu," +
                " criando uma ilusão para poder fugir!");
        gifs.mostraGif("https://i.pinimg.com/originals/f7/eb/62/f7eb62c51fe5460d49424c22b8c6d976.gif");
        this.setDesviouUltimoTurno(true);
        this.gastaDesvio();
    }
}
