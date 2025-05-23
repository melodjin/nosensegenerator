//Package
package NoSenGen.template;
//Import
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes.Name;
import java.util.regex.*;
//Import dei nostri pacchetti
import NoSenGen.myDictionary.*;

public class Template {
    //Variabili
    private String template;
    private int nounsNumber,verbsNumber, adjNumber, sentenceNumber;
    private boolean nothirdperson = false;
    private List<String> nothirdpersontoken = Arrays.asList("I", "you", "we", "they");

    //Costruttore
    public Template(String t){
        template=t;

        //Se trova una sentence, allora va aggiunto un altro template
        sentenceNumber = CountTokens("\\(sentence\\)", template);
        if (sentenceNumber > 0){
            template = template.replaceFirst("\\(sentence\\)", TemplatesLibrary.RandomTemplatePicker().getTemplate());  
        }

        //Conta tokens mancanti
        nounsNumber= CountTokens("\\(noun\\)", template);
        verbsNumber= CountTokens("\\(verb\\)", template);
        adjNumber= CountTokens("\\(adj\\)", template);
    }

    //Metodo privato che conta quanti nomi/aggettivi/verbi/sentence ci sono nel template
    private int CountTokens(String target, String sentence){
        int count = 0;

        Pattern pattern = Pattern.compile(target);
        Matcher matcher = pattern.matcher(sentence);

        while (matcher.find()) {
            count++;
        }

        return count;
    }


    //Metodo che riempie il template
    public String FillTemplate(List<MyNoun> nouns, List<MyVerb> verbs, List<MyVerb> verbs_nothirdperson, List<MyAdjective> adjectives){
        //Controllo che i nomi, verbi e aggettivi passati siano almeno quanti ne servono; altrimenti ritorno il template vuoto
        if(nouns.size() < nounsNumber){
            System.err.println("[Error]: Insufficient nouns");
            return template;
        }
        if(verbs.size() < verbsNumber){
            System.err.println("[Error]: Insufficient verbs");
            return template;
        }
        if(adjectives.size() < adjNumber){
            System.err.println("[Error]: Insufficient adjectives");
            return template;
        }

        //Creo una copia di template su cui lavorare
        String tem = template;

        //Riempie il template: dove trova (noun) mette un nome, (verb) un verbo, (adj) un aggettivo, (sentence) chiama ricorsivamente il metodo
        //Esempio di template: (noun) (verb) a (adj) (noun)
        
        //controlla se c'e' (noun) "and" (noun) e controlla se il primo nome della lista non sia in terza persona
        if (containsExactPhrase(tem, "(noun) and (noun)") || nothirdpersontoken.contains(nouns.get(0).toString())) {
            nothirdperson = true;
        }

        //Sostituisce i nomi
        for (int i = 0; i < nounsNumber; i++){
            tem = tem.replaceFirst("\\(noun\\)", nouns.get(i).toString());  
        } 
        //Sostituisce i verbi
        for (int i = 0; i < verbsNumber; i++){
            if (nothirdperson == true){
                tem = tem.replaceFirst("\\(verb\\)", verbs_nothirdperson.get(i).toString()); 
                nothirdperson = false;
            }
            else {
            tem = tem.replaceFirst("\\(verb\\)", verbs.get(i).toString());  
            }
        } 
        //Sostituisce gli aggettivi
        for (int i = 0; i < adjNumber; i++){
            tem = tem.replaceFirst("\\(adj\\)", adjectives.get(i).toString());  
        } 

        return tem;
    }

    //Metodo FillTemplate_past (non ha il controllo della terza persona)
    public String FillTemplate_past(List<MyNoun> nouns, List<MyVerb> verbs, List<MyAdjective> adjectives){
        //Controllo che i nomi, verbi e aggettivi passati siano almeno quanti ne servono; altrimenti ritorno il template vuoto
        if(nouns.size() < nounsNumber){
            System.err.println("[Error]: Insufficient nouns");
            return template;
        }
        if(verbs.size() < verbsNumber){
            System.err.println("[Error]: Insufficient verbs");
            return template;
        }
        if(adjectives.size() < adjNumber){
            System.err.println("[Error]: Insufficient adjectives");
            return template;
        }

        //Creo una copia di template su cui lavorare
        String tem = template;

        //Riempie il template: dove trova (noun) mette un nome, (verb) un verbo, (adj) un aggettivo
        //Esempio di template: (noun) (verb) a (adj) (noun)
        
        //Sostituisce i nomi
        for (int i = 0; i < nounsNumber; i++){
            tem = tem.replaceFirst("\\(noun\\)", nouns.get(i).toString());  
        } 
        //Sostituisce i verbi
        for (int i = 0; i < verbsNumber; i++){
            tem = tem.replaceFirst("\\(verb\\)", verbs.get(i).toString());  
        } 
        //Sostituisce gli aggettivi
        for (int i = 0; i < adjNumber; i++){
            tem = tem.replaceFirst("\\(adj\\)", adjectives.get(i).toString());  
        } 

        return tem;
    }

    //Metodo FillTemplate_future (non ha il controllo della terza persona)
    public String FillTemplate_future(List<MyNoun> nouns, List<MyVerb> verbs, List<MyAdjective> adjectives){
        String s = "";

        //Controllo che i nomi, verbi e aggettivi passati siano almeno quanti ne servono; altrimenti ritorno il template vuoto
        if(nouns.size() < nounsNumber){
            System.err.println("[Error]: Insufficient nouns");
            return template;
        }
        if(verbs.size() < verbsNumber){
            System.err.println("[Error]: Insufficient verbs");
            return template;
        }
        if(adjectives.size() < adjNumber){
            System.err.println("[Error]: Insufficient adjectives");
            return template;
        }

        //Creo una copia di template su cui lavorare
        String tem = template;

        //Riempie il template: dove trova (noun) mette un nome, (verb) un verbo, (adj) un aggettivo
        //Esempio di template: (noun) (verb) a (adj) (noun)
        
        //Sostituisce i nomi
        for (int i = 0; i < nounsNumber; i++){
            tem = tem.replaceFirst("\\(noun\\)", nouns.get(i).toString());  
        } 
        //Sostituisce i verbi
        for (int i = 0; i < verbsNumber; i++){
            s = "will " + verbs.get(i).toString();
            tem = tem.replaceFirst("\\(verb\\)", s);  
        } 
        //Sostituisce gli aggettivi
        for (int i = 0; i < adjNumber; i++){
            tem = tem.replaceFirst("\\(adj\\)", adjectives.get(i).toString());  
        } 

        return tem;
    }


    //Metodi get
    public String getTemplate(){
        return template;
    }

    public int getMissingVerbs(){
        return verbsNumber;
    }
    

    public int getMissingNouns(){
        return nounsNumber;
    }


    public int getMissingAdjectives(){
        return adjNumber;
    }

    //metodo cointain
    private boolean containsExactPhrase(String text, String phrase) {
        return text.contains(phrase);
    }

}
