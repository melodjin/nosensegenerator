package NoSenGen;
import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import NoSenGen.generator.Generator;

@SpringBootApplication
public class Main {

static Generator g =new Generator();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        String s = ""; //es. The quick brown fox jumps over the lazy dog, while he went home
        String result = "";
        //int tense = 2;
        // 0 = passato, 1 = presente, 2 = futuro

        Scanner scanner = new Scanner(System.in); //oggetto Scanner per leggere l'input da terminale

        //Variabili di controllo per cosa inserisce l'utente
        boolean check = false;
        int mode = 0;
        int sentenceNumber = 0, sentenceCount = 0;
        int past = 0, present = 0, future = 0;
        int temp = 0;

        System.out.println("[System]: Welcome to NoSenseGenerator! :D");

        //STEP 1: L'utente decide se inserire una frase di input o meno
        while(!check){
            System.out.println("[System]: Do you want to: \n (1).Insert a sentence that we'll analyze and use tokens \n (2).Generate a totally random sentence \n Please insert 1 or 2");
            mode = scanner.nextInt();
            scanner.nextLine(); //Consuma il carattere \n

            if(mode == 1){
                System.out.println("[System]: Insert the sentence");
                s = scanner.nextLine();
                check = true;
            } else if (mode == 2){
                check = true;
            }else {
                System.out.println("[System]: What is this number!? You can insert only 1 or 2");
            }
        }

        //Resetto le variabili di controllo
        check = false;

        //STEP 2: L'utente decide quante frasi creare
        while(!check){
            System.out.println("[System]: How many sentences do you want to create?");
            sentenceNumber = scanner.nextInt();
            scanner.nextLine(); //Consuma il carattere \n

            if(sentenceNumber > 5){
                System.out.println("[System]: Dear, we're not at NASA, do you think my computer can handle that many sentences? Enter a smaller number please");
            } else if (sentenceNumber < 1){
                System.out.println("[System]: What's the point of the program if you don't even want to create a single sentence? >:(");
            } else {
                check = true;
            }
        }

        //Resetto le variabili di controllo
        check = false;

        //STEP 3: L'utente decide quante frasi al passato, presente, futuro creare
        while(!check){
            System.out.println("[System]: How many PAST tense sentences do you want to create?");
            past = scanner.nextInt();
            temp = sentenceCount + past;
            scanner.nextLine(); //Consuma il carattere \n

            if(temp > sentenceNumber){
                System.out.println("[System]: Uh oh, you've run out of sentences, enter a smaller number");
                temp = sentenceCount;
            } else if (past < 0){
                System.out.println("[System]: Seriously you entered a NEGATIVE number?? -_-");
                temp = sentenceCount;
            } else {
                sentenceCount = temp;
                check = true;
            }
        }

        //Resetto le variabili di controllo
        check = false;

        while(!check){
            System.out.println("[System]: How many PRESENT tense sentences do you want to create?");
            present = scanner.nextInt();
            temp = sentenceCount + present;
            scanner.nextLine(); //Consuma il carattere \n

            if(temp > sentenceNumber){
                System.out.println("[System]: Uh oh, you've run out of sentences, enter a smaller number");
                temp = sentenceCount;
            } else if (present < 0){
                System.out.println("[System]: Seriously you entered a NEGATIVE number?? -_-");
                temp = sentenceCount;
            } else {
                sentenceCount = temp;
                check = true;
            }
        }

        //Resetto le variabili di controllo
        check = false;

        while(!check){
            System.out.println("[System]: How many FUTURE tense sentences do you want to create?");
            future = scanner.nextInt();
            temp = sentenceCount + future;
            scanner.nextLine(); //Consuma il carattere \n

            if(temp > sentenceNumber){
                System.out.println("[System]: Uh oh, you've run out of sentences, enter a smaller number");
                temp = sentenceCount;
            } else if (future < 0){
                System.out.println("[System]: Seriously you entered a NEGATIVE number?? -_-");
                temp = sentenceCount;
            } else {
                sentenceCount = temp;
                check = true;
            }
        }



        try {
            // Codice che potrebbe lanciare un'eccezione

            System.out.println();

            if(past > 0){
                System.out.println("[System]: Here your PAST sentences");
            }

            for (int i = 0; i < past; i++){
                result = g.genSentence(s, 0);
                System.out.println(i +1 + ": " + result);
            }

            if(present > 0){
                System.out.println("[System]: Here your PRESENT sentences");
            }

            for (int i =0; i < present; i++){
                result = g.genSentence(s, 1);
                System.out.println(i +1 + ": " + result);
            }

            if(future > 0){
                System.out.println("[System]: Here your FUTURE sentences");
            }

            for (int i =0; i < future; i++){
                result = g.genSentence(s, 1);
                System.out.println(i +1 + ": " + result);
            }

            System.out.println("[System]: Thank you for using NoSenseGenerator, hope to see you soon ^_^");


            // System.out.println("Frase iniziale: " + s);
            // result = g.genSentence(s,tense);
            // System.out.println(result);

        } catch (Exception e) {
            //Gestione dell'eccezione
            System.out.println("[Error]: " + e.getMessage());
        }
    }
}



