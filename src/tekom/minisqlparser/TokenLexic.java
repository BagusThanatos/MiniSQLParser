/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekom.minisqlparser;

/**
 *
 * @author BagusThanatos
 */
public class TokenLexic {
    /*
    class yang nantinya mengandung hasil parsing
    */
    private int tokenCode;
    private String string;
    private String value;
    
    public TokenLexic(int tc, String s, String v){
        this.tokenCode=tc;
        if (tc<=Parser.KEYWORDS) this.string="Keyword";
        else if(tc<=Parser.BOOLEANS) this.string="Boolean Operator";
        else if(tc<=Parser.LOGIC_OPERATORS) this.string="Logic Operator";
        else if (tc<=Parser.SET_OPERATOR) this.string="Set Operator";
        else this.string=s;
        this.value=v;
    }

    public int getTokenCode(){
        return this.tokenCode;
    }
    public String getName(){
        return this.string;
    }
    public String getValue(){
        return this.value;
    }
}
