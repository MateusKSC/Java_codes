package ScreenMatchPKG.Principal;
import ScreenMatchPKG.Excecao.YearConvertionException;
import ScreenMatchPKG.Modelos.Titulo;
import ScreenMatchPKG.Modelos.TituloOmdb;
import com.google.gson.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PrincipalComBuscaHTTP  {
    public static void main (String[]args) throws IOException, InterruptedException {
        String movie = "";
        String json;
        Scanner teclado = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        List<Titulo> titulos = new ArrayList<>();

        while(!(movie.equalsIgnoreCase("sair"))) {
            System.out.println("Me diga um filme!");
            movie = teclado.nextLine();

            if (movie.equalsIgnoreCase("sair")) {
                break;
            }

            movie = movie.replace(' ', '+');
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://www.omdbapi.com/?t=" + movie + "&apikey=a468d5f2"))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            json = response.body();
            System.out.println(json);
            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            try {
                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println(meuTitulo);
                titulos.add(meuTitulo);
            } catch (YearConvertionException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unidentified Exception");
            }

        }
        System.out.println(titulos);
        FileWriter escrita = new FileWriter("filmes.json");
        escrita.write(gson.toJson(titulos));
        escrita.close();
    }
}