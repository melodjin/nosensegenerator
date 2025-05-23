//Package
package NoSenGen.myDictionary;

public class MyNoun extends Token{
    public MyNoun(String n){
        super(n);
    }

    public String getMyNoun(){
        return this.getToken();
    }

    public void setMyNoun(String n){
        this.setToken(n);
    }
}
