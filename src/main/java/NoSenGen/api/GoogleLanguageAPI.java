//Package
package NoSenGen.api;
//Import
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//import org.json.*;
//Import dei nostri pacchetti
import NoSenGen.myDictionary.*;
import org.json.JSONArray;
import org.json.JSONObject;



//DEBUG
//NON SERVE PIU' COMPILARE COSI': bisogna usare Git Bash e il file .sh, e' tutto scritto la' dentro, lo lascio solo come Debug
//Compilare con: javac -cp ".;json-20250107.jar" GoogleLanguageAPI.java
//Eseguire con: java -cp ".;json-20250107.jar" GoogleLanguageAPI.java

public class GoogleLanguageAPI {
        //Variabili 
        private static ArrayList<MyNoun> nouns = new ArrayList<>();
        private static ArrayList<MyVerb> verbs = new ArrayList<>();
        private static ArrayList<MyVerb> verbs_thirdperson = new ArrayList<>();
        private static ArrayList<MyVerb> verbs_past = new ArrayList<>();
        private static ArrayList<MyAdjective> adj = new ArrayList<>();

    public static void LanguageApi(String sentence) throws Exception {
        //API Key
        String apiKey = "AIzaSyCnUvmTiz84QCIpInKTtlufK7TXMzL2rZg"; //Chiave Fede
        
        //Endpoint dell'API
        String url = "https://language.googleapis.com/v1/documents:analyzeSyntax?key=" + apiKey;
        
        //Testo JSON per la richiesta
        String requestBody = "{\n" +
            "  \"document\": {\n" +
            "    \"type\": \"PLAIN_TEXT\",\n" +
            "    \"content\": \"" + sentence + "\"\n" +
            "  },\n" +
            "  \"encodingType\": \"UTF8\"\n" +
            "}";
        
        //Crea un client HTTP
        HttpClient client = HttpClient.newHttpClient();

        //Crea una richiesta HTTP POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        //Invia la richiesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Gestione degli errori
        if (response.statusCode() != 200) {
            System.out.println("Error: " + response.statusCode());
            System.out.println("Details: " + response.body());
        }

        // //Stampa la risposta raw
        // System.out.println("Response Code: " + response.statusCode());
        // System.out.println("Response Body: " + response.body());

        //La risposta JSON dell'api la metto in una stringa
        String jsonResponse = response.body();

        //Parsing del JSON
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray tokens = jsonObject.getJSONArray("tokens");

        //Estrapola i tokens
        for (int i = 0; i < tokens.length(); i++) {
            JSONObject token = tokens.getJSONObject(i);
            String word = token.getJSONObject("text").getString("content");
            String posTag = token.getJSONObject("partOfSpeech").getString("tag");

            String person = token.getJSONObject("partOfSpeech").getString("person");
            String temp = token.getJSONObject("partOfSpeech").getString("tense");

            //Salva le parole in array
            switch (posTag) {
            case "PRON":
                nouns.add(new MyNoun(word));
                break;
            case "NOUN":
                nouns.add(new MyNoun(word));
                break;
            case "VERB":
                if(person.equals("THIRD")){
                    verbs_thirdperson.add(new MyVerb(word));
                } else if (temp.equals("PAST")){
                    verbs_past.add(new MyVerb(word));
                } else {
                    verbs.add(new MyVerb(word));
                }
                break;
            case "ADJ":
                adj.add(new MyAdjective(word));
                break;
            }
        }


        // //Debug: Stampo gli array  
        // System.out.println("Nomi: " + nouns);
        // System.out.println("Verbi 3a persona: " + verbs_thirdperson);
        // System.out.println("Verbi generali: " + verbs);
        // System.out.println("Verbi al passto: " + verbs_past);
        // System.out.println("Aggettivi: " + adj);


    }
    
    //metodi get
    public static ArrayList<MyNoun> getNouns() {
        return nouns;
    }

    public static ArrayList<MyVerb> getVerbs() {
        return verbs;
    }

        public static ArrayList<MyVerb> getVerbs_thirdperson() {
        return verbs_thirdperson;
    }

    public static ArrayList<MyVerb> getVerbs_past() {
        return verbs_past;
    }

    public static ArrayList<MyAdjective> getAdj() {
        return adj;
    }
    
}

