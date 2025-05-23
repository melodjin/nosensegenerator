//Package
package NoSenGen.myDictionary;


public class Token {
    public Token(String s){
        token=s;
    }

    public Token(){
        token=null;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String t){
        token=t;
    }

    public String toString(){
        return this.getToken();
    }

    private String token;
}