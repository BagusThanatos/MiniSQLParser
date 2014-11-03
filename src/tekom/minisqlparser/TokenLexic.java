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
    
    public final static String KEYWORD="Keyword";
    public final static String VARIABLE="Variable";
    public final static String CONSTANT="Constant";
    public final static String BOOLEAN_OPERATOR="Boolean Opetarot";
    public final static String LOGIC_OPERATOR="Logic Operator";
    public final static String SET_OPERATOR="Set Operator";
    
    public TokenLexic(int tc, String s, String v){
        this.tokenCode=tc;
        this.string=s;
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
