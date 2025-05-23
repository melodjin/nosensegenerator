//Package
package NoSenGen.myDictionary;

public class MyVerb extends Token {
    public MyVerb (String v){
        super(v);
    }

    public String getMyVerb(){
        return this.getToken();
    }

    public void setMyVerb(String n){
        this.setToken(n);
    }
}
