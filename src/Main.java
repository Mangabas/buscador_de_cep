import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String sair = "";
        ArrayList lista = new ArrayList<>();
        String cep = "";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        while(!cep.equalsIgnoreCase("sair")) {

            Scanner leitura = new Scanner(System.in);
            System.out.println("Digite um endereço CEP de  8 digitos");
            System.out.println("\nDigite sair para finalizar o programa");
            cep = leitura.nextLine();
            if (cep.equalsIgnoreCase("sair")){
                break;
            }
            String endereco = "https://viacep.com.br/ws/" + cep + "/json/";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            Cep meuCep = gson.fromJson(json, Cep.class);
            lista.add(meuCep);

            System.out.println(meuCep);
        }
        FileWriter arquivo = new FileWriter("CEPs.json");
        arquivo.write(gson.toJson(lista));

        arquivo.close();
    }
}
