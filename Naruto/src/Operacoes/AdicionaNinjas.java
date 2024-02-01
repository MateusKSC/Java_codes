package Operacoes;

import Ninjas.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdicionaNinjas {
    /**
     * Função responsável por buscar na API Naruto DB os ninjas informados pelo jogador e por
     * adicioná-los em um array de objetos do tipo "Personagem" de modo a repassar este array
     * para a classe "Luta".
     */
    public void registraNinjas() {
        String ninja = "";
        String json = "";
        int quantidadeNinjas = 0;
        String proficienciaNinja = "";
        List<Integer> ninjasTipos = new ArrayList<>();
        List<Personagem> personagens = new ArrayList<>();
        Luta luta = new Luta();
        GeradorDeImagens geradorDeImagens = new GeradorDeImagens();
        Scanner teclado = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        while (!(ninja.equalsIgnoreCase("sair")) && !(proficienciaNinja.equals("4"))
                && quantidadeNinjas < 2) {


            System.out.println("Me diga um ninja usando o seu nome completo ou digite sair!");
            ninja = teclado.nextLine();


            if (ninja.equalsIgnoreCase("sair") || quantidadeNinjas == 2) {
                System.out.println("Até logo!");
                break;
            }

            ninja = ninja.replace(' ', '+');
            HttpClient client = HttpClient.newHttpClient();

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://narutodb.xyz/api/character/search?name=" + ninja))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                json = response.body();
            } catch (Exception e) {
                System.out.println("Houve um problema de conexão com a API Naruto DB");
                break;
            }

            if (json.contains("not found") || ninja.isEmpty()) {
                System.out.println("Ninja não encontrado, tente novamente ou digite Sair.");
            } else {
                PersonagemNarutoDB personagemDB = gson.fromJson(json, PersonagemNarutoDB.class);
                Personagem personagemNinjaAmostra = new Personagem(personagemDB);
                System.out.println("""
                        Agora, em qual especialidades o ninja possui
                        a maior proficiêcia? Escolha um número: 
                        1: Ninjutsu
                        2: Genjutsu
                        3: Taijutsu
                        4: Sair""");
                proficienciaNinja = teclado.nextLine();

                if (proficienciaNinja.equals("1")) {
                    NinjaDeNinjutsu ninjaNinjutsu = new NinjaDeNinjutsu(personagemNinjaAmostra);
                    personagens.add(ninjaNinjutsu);
                    ninjasTipos.add(1);
                    System.out.println(personagens);
                } else if (proficienciaNinja.equals("2")) {
                    NinjaDeGenjutsu ninjaGenjutsu = new NinjaDeGenjutsu(personagemNinjaAmostra);
                    personagens.add(ninjaGenjutsu);
                    ninjasTipos.add(2);
                    System.out.println(personagens);
                } else if (proficienciaNinja.equals("3")) {
                    NinjaDeTaijutsu ninjaTaijutsu = new NinjaDeTaijutsu(personagemNinjaAmostra);
                    personagens.add(ninjaTaijutsu);
                    ninjasTipos.add(3);
                    System.out.println(personagens);
                } else if (proficienciaNinja.equals("4")) {
                    System.out.println("Até logo!");
                    break;
                }
                geradorDeImagens.mostraRetratos(personagemNinjaAmostra.getRetratos().get(0));
                quantidadeNinjas++;
            }
        }
        if (quantidadeNinjas != 0) {
            System.out.println("Os combatentes foram escolhidos!");
            luta.regeAdversarios(personagens);
        }
    }
}
