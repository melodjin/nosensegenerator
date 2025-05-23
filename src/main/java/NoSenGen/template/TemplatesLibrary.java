//Package
package NoSenGen.template;
//Import
import java.util.*;
import java.io.*;
import java.security.SecureRandom;

//Questa classe crea gli oggetti template
public class TemplatesLibrary{
    //Variabili
    static ArrayList<Template> templates = new ArrayList<>();

    // //Debug: main
    // public static void main(String[] args) {
    //     //Debug: Stampo i template ricevuti
    //     for(int i = 0; i< templates.size(); i++){
    //         System.out.println(i + ": " + templates.get(i).getTemplate().toString());
    //     }

    // }

    static {
        templates = TemplateAdder("src/main/resources/templates.txt");

        if (templates.isEmpty()) {
        System.err.println("[Error]: No templates found in templates.txt");
        }


    }

    //FileReader e Crea template
    private static ArrayList<Template> TemplateAdder(String filenoun){

        try (BufferedReader reader = new BufferedReader(new FileReader(filenoun))) {
            String riga;

            while ((riga = reader.readLine()) != null) {
                templates.add(new Template(riga));

            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura: " + e.getMessage());
        }

        return templates;
    }

    //Metodo RandomPicker
    public static Template RandomTemplatePicker(){
        //Creo un numero randoom
        SecureRandom rand = new SecureRandom();
        int result = 0;

        result = rand.nextInt(templates.size());

        return templates.get(result);

    }

    //Metodo RandomPicker di template senza (sentence), questo per evitare che escano frasi troppo lunghe
    public static Template RandomTemplatePicker_nosentence(){
        //Variabili
        int result = 0;
        int max = 10;

        //Controllo che la size sia maggiore di 10
        // if (templates.size() < max){
        //     max = templates.size();
        // }

        //Creo un numero randoom
        SecureRandom rand = new SecureRandom();
        result = 0;

        result = rand.nextInt(max);

        return templates.get(result);
    }
}