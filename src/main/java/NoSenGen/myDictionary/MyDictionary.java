//Package
package NoSenGen.myDictionary;

//Import
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class MyDictionary {
    //Costruttore
    public MyDictionary() throws IOException{
        nouns=setList("src/main/resources/Nouns.txt", MyNoun::new);

        verbs=setList("src/main/resources/Verbs_thirdperson.txt", MyVerb::new);
        verbs_nothirdperson=setList("src/main/Resources/Verbs.txt", MyVerb::new);
        verbs_past=setList("src/main/Resources/Verbs_past.txt", MyVerb::new);

        adjs=setList("src/main/Resources/Adjs.txt", MyAdjective::new);
    }
 
    //metodo usato nel costruttore
    private static <T> List<T> setList(String fileMyNoun, Function<String, T> creator) throws IOException{
        FileReader file=new FileReader(fileMyNoun);
        Scanner sc=new Scanner(file);
        List<T> result=new ArrayList<>();
        while (sc.hasNextLine()) {
            String line=sc.nextLine();
            result.add(creator.apply(line));
        }

        sc.close();
        return result;
    }

    //Aggiungere verbi in terza persona e past
    
//Metodi get
    public MyNoun getNoun(){
        return chooseToken(nouns);
    }

    public MyVerb getVerb(){
        return chooseToken(verbs);
    }

    public MyVerb getVerb_nothirdperson(){
        return chooseToken(verbs_nothirdperson);
    }

    public MyVerb getVerb_past(){
        return chooseToken(verbs_past);
    }

    public MyAdjective getAdj(){
        return chooseToken(adjs);
    }

    //metodi Random
    private <T> T chooseToken(List<T> l){
        Random rand=new Random();
        T t=l.get(rand.nextInt(l.size()));
        return t;
    }

// METODI DI DEBUG  

    //Debug: metodo print
    private static <T> void printList(List<T> list){
        for (T t : list) {
            System.out.println(t);
        }
    }


    //Variabili
    private List<MyNoun> nouns;
    private List<MyVerb> verbs, verbs_nothirdperson, verbs_past;
    private List<MyAdjective> adjs;


}
