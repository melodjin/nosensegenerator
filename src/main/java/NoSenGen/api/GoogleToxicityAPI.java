// Package
package NoSenGen.api;

// Import
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.json.*;

// Classe
public class GoogleToxicityAPI {

    //Metodo per verificare se il contenuto e's accettabile
    public static boolean isToxicityAcceptable(String sentence) throws Exception {
        //API Key
        String apiKey = "AIzaSyCnUvmTiz84QCIpInKTtlufK7TXMzL2rZg"; //Chiave Fede

        //Endpoint dell'API
        String url = "https://language.googleapis.com/v1/documents:moderateText?key=" + apiKey;

        //Corpo JSON della richiesta
        String requestBody = "{\n" +
                "  \"document\": {\n" +
                "    \"type\": \"PLAIN_TEXT\",\n" +
                "    \"content\": \"" + sentence + "\"\n" +
                "  }\n" +
                "}";

        //Client HTTP
        HttpClient client = HttpClient.newHttpClient();

        //Richiesta HTTP POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        //Invio richiesta e ricezione risposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Controllo errori
        if (response.statusCode() != 200) {
            System.out.println("Errore: " + response.statusCode());
            System.out.println("Dettagli: " + response.body());
            return false; // Default in caso di errore
        }

        //Parsing della risposta JSON
        String jsonResponse = response.body();
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray categories = jsonObject.getJSONArray("moderationCategories");

        //Analizza le categorie
        for (int i = 0; i < categories.length(); i++) {
            JSONObject category = categories.getJSONObject(i);
            String name = category.getString("name");
            double confidence = category.getDouble("confidence");

            // Se una categoria e' "troppo sicura" di avere contenuto tossico, la consideriamo inaccettabile
            if (confidence >= 0.6) { //Soglia di tolleranza
                //Spiega perche' e' tossica
                //System.out.println("[Debug]: Contenuto problematico rilevato: " + name + " (" + confidence + ")");
                return false;
            } else{
                //Debug: Print toxicity
                // System.out.println("Contenuto problematico NON rilevato: " + name + " (" + confidence + ")");
            }
        }

        //Nessuna categoria problematica rilevata
        return true;
    }
}
