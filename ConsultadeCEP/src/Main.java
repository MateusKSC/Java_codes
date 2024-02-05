import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String jsonText = "";
        String endereco = "";
        List listaEnderecos = new ArrayList<>();
        Scanner cep = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        while(!(endereco.equalsIgnoreCase("sair"))) {
            System.out.println("Me diga um CEP ou digite sair");
            endereco = cep.nextLine();
            if (endereco.equalsIgnoreCase("sair")){
                break;
            }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://viacep.com.br/ws/" + endereco + "/json/"))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            jsonText = response.body();
            Endereco endereco1 = new Endereco();
            endereco1 = gson.fromJson(jsonText, Endereco.class);
            System.out.println(endereco1);
            if(endereco1.getCep() != null) {
                listaEnderecos.add(endereco1);
            }
            else {
                System.out.println("CEP não encontrado");
            }
            }catch (Exception e){
                System.out.println("Deu ruim");
            }
        }
        System.out.println(listaEnderecos);
        FileWriter escritaArquivo= new FileWriter("enderecos.json");
        escritaArquivo.write(gson.toJson(listaEnderecos));
        escritaArquivo.close();

    }
}