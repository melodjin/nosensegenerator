//Package
package NoSenGen.generator;
//Import
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
//Import di pacchetti di classi nostre
import org.springframework.stereotype.Component;
import NoSenGen.myDictionary.*;

import NoSenGen.api.*;
import NoSenGen.template.*;
import org.springframework.stereotype.Service;



@Component
public class Generator {
    //Costruttore vuoto (non deve fare nulla, serve solo per chiamare genSentence)
    public Generator(){

    }
    
    /*metodo alla base di tutto: genera una sentence random dalla sentence immessa dall'utente */
    public String genSentence(String sentenceIn, int tense) throws IOException{
        //Variabili del metodo
        MyDictionary dict=new MyDictionary();

//----------------------------------------------------- (1) COLLEGAMENTO API E ANALISI FRASE -----------------------------------------------------\\

       try{GoogleLanguageAPI.LanguageApi(sentenceIn);
       }catch(Exception e){
        //Gestione dell'eccezione
        System.out.println("[Error]: " + e.getMessage());
       }

        nounList.addAll(GoogleLanguageAPI.getNouns());
        adjList.addAll(GoogleLanguageAPI.getAdj());
        verbList_nothirdperson.addAll(GoogleLanguageAPI.getVerbs());
        verbList.addAll(GoogleLanguageAPI.getVerbs_thirdperson());
        verbList_past.addAll(GoogleLanguageAPI.getVerbs_past());


//----------------------------------------------------- (2) COSTRUISCI LA FRASE RANDOM -----------------------------------------------------\\


        // nounList.add(new MyNoun("god"));
        // adjList.add(new MyAdjective("bastard"));
        // verbList.add(new MyVerb("fuck"));
        
        temp= TemplatesLibrary.RandomTemplatePicker();
        template = temp; //creo una copia del template su cui lavorare, nel caso la toxicity non sia accettabile, ritorno alla copia originale

        //riempi le liste
        fillList(nounList, template.getMissingNouns(), dict::getNoun);

        fillList(verbList, template.getMissingVerbs(), dict::getVerb);
        fillList(verbList_nothirdperson, template.getMissingVerbs(), dict::getVerb_nothirdperson);
        fillList(verbList_past, template.getMissingVerbs(), dict::getVerb_past);
        
        fillList(adjList, template.getMissingAdjectives(), dict::getAdj);

        Collections.shuffle(nounList);
        Collections.shuffle(verbList);
        Collections.shuffle(verbList_nothirdperson);
        Collections.shuffle(verbList_past);
        Collections.shuffle(adjList);

        //Si filla con i verbi al presente o al passato in base a cosa chiede l'utente
        if(tense == 0){
            //Verbi al passato
            sentenceOut=template.FillTemplate_past(nounList, verbList_past, adjList);
        } else if (tense == 2) {
            //Verbi al futuro
            sentenceOut=template.FillTemplate_future(nounList, verbList_nothirdperson, adjList);
        } else {
            //Verbi al presente (tense == 1)
            sentenceOut=template.FillTemplate(nounList, verbList, verbList_nothirdperson, adjList);
        }
        
        
//----------------------------------------------------- (3) CONTROLLA LA TOSSICITA' -----------------------------------------------------\\

        try {
            if(!GoogleToxicityAPI.isToxicityAcceptable(sentenceOut)){
                sentenceOut = genSentence(sentenceIn, tense);
                //System.out.println("Tossica");
            }
        }catch(Exception e){
        //Gestione dell'eccezione
        System.out.println("[Error]: " + e.getMessage());
       }

//----------------------------------------------------- (4) RISULTATO -----------------------------------------------------\\

        //Svuota le liste
        nounList.clear();
        adjList.clear();
        verbList.clear();
        verbList_nothirdperson.clear();
        verbList_past.clear();

        //Ritorna la frase
        return sentenceOut;
    }

    private static <T> void fillList(List<T> list, int targetSize, Supplier<T> sup){
        while (list.size() < targetSize){
            list.add(sup.get());
        }
    }




    //Varibabili
    private List<MyNoun> nounList=new ArrayList<>();
    private List<MyAdjective> adjList=new ArrayList<>();
    private List<MyVerb> verbList=new ArrayList<>();
    private List<MyVerb> verbList_nothirdperson=new ArrayList<>();
    private List<MyVerb> verbList_past=new ArrayList<>();
    private String sentenceOut="";
    private Template temp, template;


}
