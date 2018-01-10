package br.com.alura.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by italo on 09/01/18.
 */

public class WebClient {
    public String post(String JSON){
        try {
            //Especificando a URL e criando um objeto conexão
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Setando os parâmetros da conexão como query e resposta em JSON
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            //Informando o objeto que vamos escrever na saída padrão
            connection.setDoOutput(true);
            //Usando um PrintStream para facilitar o uso do getOutputStream
            PrintStream output = new PrintStream(connection.getOutputStream());
            //Imprimindo o JSON
            output.println(JSON);
            //Conectando com o server
            connection.connect();


            Scanner input = new Scanner(connection.getInputStream());
            String resposta = input.next();
            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    return null;
    }
}
