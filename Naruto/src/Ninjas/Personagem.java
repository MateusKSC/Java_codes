package Ninjas;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

/**
 * Classe mãe de todos os tipos de personagens ninja.
 * @author mateus.scheuermann
 * @version 1.0.0
 */
public class Personagem {
    private String name;
    private List<String> retratos;
    private String idade;
    private String clan;
    private List<String> jutsus;
    private int chakra = 1000;
    private int vida = 30;
    private int desvios = 3;
    private boolean desviouUltimoTurno = false;
    public Personagem() {
    }

    public Personagem(PersonagemNarutoDB personagem) {
        JsonObject ninjaJson;
        this.jutsus = new ArrayList<String>();
        this.retratos = new ArrayList<String>();
        this.name = personagem.name();
        this.retratos = personagem.images();
        this.jutsus = personagem.jutsu();
        try{
        ninjaJson = personagem.personal().get("age").getAsJsonObject();
        this.idade = ninjaJson.get("Part II").getAsString().substring(0,2);
        }catch (Exception e){
            this.idade = "Nenhuma idade";
        }
        try{
            this.clan = personagem.personal().get("clan").getAsString();
        }catch (Exception e2){
            this.clan = "nenhum clan";
        }
    }
    public Personagem(String name, String idade, String clan, List<String> jutsus, int chakra, int vida, int desvio) {
        this.name = name;
        this.idade = idade;
        this.clan = clan;
        this.jutsus = jutsus;
        for (int i = 0; i < this.jutsus.size(); i++) {
            this.jutsus.set(i, this.jutsus.get(i).toLowerCase());
        }
        this.chakra = chakra;
        this.vida = vida;
        this.desvios = desvio;
    }

    public void setJutsus(List<String> jutsus) {
        this.jutsus = jutsus;
    }

    public void setChakra(int chakra) {
        this.chakra = chakra;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setRetratos(List<String> retratos) {
        this.retratos = retratos;
    }

    public boolean isDesviouUltimoTurno() {
        return desviouUltimoTurno;
    }

    public void setDesviouUltimoTurno(boolean desviouUltimoTurno) {
        this.desviouUltimoTurno = desviouUltimoTurno;
    }

    public int getVida() {
        return vida;
    }

    public int getDesvios() {
        return desvios;
    }


    /**
     * Uma função que é chamada toda vez que a função usarJutsu específica de cada subclasse da classe
     * "Pesonagem" for invocada e que calcula o gasto do recurso utilizado pelas habilidades de cada
     * personagem, o chakra, verificando se ainda existe chakra disponível e usando como base um valor
     * de gasto padrão com um multiplicador fornecido pela função que invocou este método. Além disso,
     * limita o valor máximo do multiplicador com o objetivo de aumentar a duração do jogo.
     * @param jutsu parâmetro multiplicador do gasto do recurso chakra.
     * @Returns resultado, com valor "true ou "false", da verificação feita ao confirmar se o ninja ainda
     * possuirá uma quantidade maior ou igual a zero de chakra após lançar o jutsu, retornando "true" caso
     * possuir e "false" caso não.
     */
    public boolean gastaChakra(int jutsu){
        boolean result;
        if(jutsu >= 5){
            jutsu = 5;
        }
        if(this.chakra - jutsu*100 >= 0) {

            this.chakra -= jutsu * 100;
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }
    /**
     * Uma função que é chamada toda vez que o ninja conseguir lançar um jutsu com sucesso.
     * Este método é responsável por reduzir a vida do ninja adversário toda vez que o mesmo
     * for acertado por um jutsu e utilizando um multiplicador fornecido pela função que invocou
     * este método. Além disso, limita o valor máximo do multiplicador com o objetivo de aumentar
     * a duração do jogo.
     * @param jutsu parâmetro multiplicador do gasto do recurso chakra.
     * @Returns resultado, com valor "true ou "false", da verificação se o ninja ainda possuirá um
     * valor de vida maior ou igual a zero após receber o dano da habilidade adversária, retornando
     * "true" caso possuir e "false" caso não.
     */
    public boolean recebeDano(int jutsu){
        if(jutsu >= 3){
            jutsu = 3;
        }
        if(this.vida - jutsu*5 >= 0) {

            this.vida -= jutsu * 5;
            return true;
        }
        else{
            this.vida = 0;
            return false;
        }
    }
    public void gastaDesvio(){
        if(this.desvios > 0) {
            this.desvios--;
        }
        else {
            System.out.println("Você não pode mais desviar!");
        }    }
    public List<String> getRetratos() {
        return retratos;
    }

    public String getIdade() {
        return idade;
    }

    public String getClan() {
        return clan;
    }

    public List<String> getJutsus() {
        return jutsus;
    }


    public int getChakra() {
        return chakra;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Personagem{" +
                "name='" + name + "\n" +
                ", idade=" + idade + "\n" +
                ", clan='" + clan + "\n" +
                ", jutsus=" + jutsus + "\n" +
                '}';
    }

    public void usarJutsu(Personagem personagem) {
        System.out.println("Usando jutsu");
    }
    public void desviar() {
        System.out.println("Desviando");
    }
}

