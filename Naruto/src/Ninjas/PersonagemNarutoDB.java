package Ninjas;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Java record responsável por receber de forma formatada e ordenada os valores enviados pela API
 * Naruto DB, onde cada variável teve o seu nome especificado para ser idêntico aos
 * nomes de cada item dentro do texto recebido em formato json.
 * @param name nome do ninja;
 * @param images imagem para uso de retrato do ninja;
 * @param age idade do ninja;
 * @param clan clan do ninja;
 * @param jutsu jutsus do ninja;
 * @param personal objeto pertencente ao texto em json que é acessado para obter a informação da
 * idade do ninja.
 */
public record PersonagemNarutoDB(String name, List<String> images, String age, String clan, List<String> jutsu, JsonObject personal) {
}
