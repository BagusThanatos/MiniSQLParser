/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekom.minisqlparser;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
/**
 *
 * @author BagusThanatos
 */
public class Parser {
    /*
    Class untuk melakukan parsing terhadap inputan yang diberikan
    */
    private final static Stack<String> s = new Stack();
    protected final static ArrayList<String> lexicalName= new ArrayList();
    protected final static ArrayList<Integer> lexicalCode= new ArrayList();
    static {
        lexicalName.add("SELECT");  //1
        lexicalName.add("*");       //2
        lexicalName.add("WHERE");   //3
        lexicalName.add("FROM");    //4
        lexicalName.add("(");       //5
        lexicalName.add(")");       //6
        lexicalName.add(".");       //7
        lexicalName.add(",");       //8
        lexicalName.add(";");       //9
        lexicalName.add("AND");     //10
        lexicalName.add("OR");      //11
        lexicalName.add("NOT");     //12
        lexicalName.add(">=");      //13
        lexicalName.add("=");       //14
        lexicalName.add("<=");      //15
        lexicalName.add("<");       //16
        lexicalName.add(">");       //17
        lexicalName.add("LIKE");    //18
        lexicalName.add("UNION");   //19
        lexicalName.add("JOIN");    //20
        for (int i=1;i<=20;i++) lexicalCode.add(i);
    }
    
    public final static int UNIDENTIFIED=24;
    public final static int KEYWORDS=9;
    public final static int BOOLEANS=12;
    public final static int LOGIC_OPERATORS=18;
    public final static int SET_OPERATOR=20;
    public final static int VARIABLE=21;
    public final static int CONSTANT_STRING=22;
    public final static int CONSTANT_NUMBER=23;
    public static ArrayList<Integer> parseSQL(String sql){
        /*
        akan mengembalikan bentuk berupa kode lexical dari inputan String sql yang ada
        */
        ArrayList<Integer> result = new ArrayList();
        String substring;
        int nexts= -1;
        char firstChar;
        while (!sql.isEmpty()){
            sql=sql.trim();
            nexts=nextSymbol(sql);
            firstChar=sql.charAt(0);
            substring= sql.substring(0,nexts==0? 1:nexts);
            if (!substring.equals(sql)) sql=sql.substring(nexts) ;
            else sql="";
            if (lexicalName.contains(substring.toUpperCase())) result.add(lexicalCode.get(lexicalName.indexOf(substring.toUpperCase())));
            else result.add(VARIABLE);
            System.out.println(substring);
        }
        return result;
    }
    public static ArrayList<TokenLexic> parseSQL2(String sql){
        /*
        memiliki fungsi yang sama dengan fungsi parseSQL, hanya saja menggunakan StringTokenizer
        */
        boolean logical=false;
        boolean stringWithSpace=false;
        boolean realNumber=false;
        String stringRealNumber="";
        String stringWithSpaceTemp="";
        String logicalString="";
        ArrayList<TokenLexic> result = new ArrayList();
        String temp;
        sql=sql.replace("\n", " ");
        sql=sql.replace("\t", " ");
        StringTokenizer st= new StringTokenizer(sql,"*,.<>=(); ",true);
        while(st.hasMoreTokens()){
            temp=st.nextToken();
            if (stringWithSpace && !temp.contains("\"")) {
                stringWithSpaceTemp+=temp;
            }
            else if (!temp.equals(" ")){
                if (stringWithSpace) {
                    if (temp.charAt(temp.length()-1)=='\"') 
                        result.add(new TokenLexic(CONSTANT_STRING, "Constant String", stringWithSpaceTemp+temp));
                    else if (temp.contains("\"")) 
                        result.add(new TokenLexic(UNIDENTIFIED, "Unidentified", stringWithSpaceTemp+" "+temp)); 
                    /*
                    else {
                        stringWithSpaceTemp+=" "+temp;
                        continue;
                    }*/
                    stringWithSpace=false;
                    stringWithSpaceTemp="";
                }
                else if (temp.charAt(0)== '\"' && temp.charAt(temp.length()-1) != '\"') {
                    stringWithSpace =true;
                    stringWithSpaceTemp=temp;
                }
                /*
                else if (temp.contains("\"") || temp.contains("\'") || temp.contains("“")) 
                    result.add(new TokenLexic(CONSTANT,"Constant",temp));
                */
                else if (temp.matches("^[0-9]+$")) {
                    if (realNumber){
                        result.add(new TokenLexic(CONSTANT_NUMBER,"Constant Number",stringRealNumber+temp));
                        realNumber=false;
                        stringRealNumber="";
                    }
                    else {
                        realNumber=true;
                        stringRealNumber=temp;
                    }
                    
                }
                else if (temp.equals(".")) {
                    if (realNumber) stringRealNumber+=".";
                    else result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(temp.toUpperCase())),"",temp));
                }
                else if (temp.equals("=")) {
                    if (logical) {
                        logical=false;
                        result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(logicalString+temp)), "", logicalString+temp));   
                    }
                    else 
                        result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf("=")),"" , "="));
                    logicalString="";
                }
                else if (temp.equals(">")|| temp.equals("<")) {
                    if (logical){
                        logical=false;
                        if (temp.equals(">") || temp.equals("<")){
                            result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(logicalString)),"",logicalString));
                            result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(temp)),"",temp));
                        }
                        logicalString="";
                    }
                    else {
                        logical=true;
                        logicalString=temp;
                    }
                }
                else if (lexicalName.contains(temp.toUpperCase())) 
                    result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(temp.toUpperCase())),"",temp));
                else result.add(new TokenLexic(VARIABLE, "Variable" , temp));
                if (logical && !temp.equals("=") && !temp.equals(">")&& !temp.equals("<")) {
                    logical= false;
                    result.add(realNumber || stringWithSpace?result.size():result.size()-2, new TokenLexic(lexicalCode.get(lexicalName.indexOf(logicalString)), "", logicalString));
                    logicalString="";
                }
                if (realNumber && !temp.matches("^[0-9]+$") && !temp.equals(".")) {
                    realNumber=false;
                    if (stringRealNumber.contains(".")) {
                        result.add(result.size()-2, new TokenLexic(CONSTANT_NUMBER, "Constant Number", stringRealNumber.substring(0, stringRealNumber.length()-1)));
                        result.add(result.size()-2, new TokenLexic(lexicalCode.get(lexicalName.indexOf(".")), "", "."));
                    }
                    else {
                        result.add(logical?result.size():result.size()-1,new TokenLexic(CONSTANT_NUMBER, "Constant Number", stringRealNumber));
                    }
                }
            }
        }
        if (stringWithSpace) result.add(new TokenLexic(CONSTANT_STRING, "Constant String", stringWithSpaceTemp));
        if (logical)
            result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(logicalString)), "", logicalString));
        if (realNumber) result.add(new  TokenLexic(CONSTANT_NUMBER, "Constant Number", stringRealNumber));
        
        return result;
    }
    public static ArrayList<Integer> parseLexical(String l){
        /*
        melakukan parsing terhadap inputan yang berupa simbol lexical, seperti: 1 2 11
        masing masing simbol dipisahkan spasi
        */
        
        ArrayList<Integer> result=new ArrayList();
        StringTokenizer st = new StringTokenizer(l);
        
        while (st.hasMoreTokens()) result.add(Integer.parseInt(st.nextToken()));
        /*
        while(!l.isEmpty()){
            System.out.println(l);
            l=l.trim();
            if (l.isEmpty()) return result;
            result.add(Integer.parseInt(l.substring(0,l.contains(" ") ? l.indexOf(" "): l.length())));
            if (l.contains(" ")) l=l.substring(l.indexOf(" ")+1);
            else l="";
        }
        */
        return result;
    }
    public static int nextSymbol(String sql){
        /*
        akan mengembalikan posisi spasi atau simbol terdekat,
        akan mengembalikan nilai panjang string jika tidak ditemukan spasi atau simbol terdekat
        */
        int comma=!sql.contains(",")? 32000:sql.indexOf(","),period=!sql.contains(".")? 32000:sql.indexOf("."),
                space=!sql.contains(" ")? 32000:sql.indexOf(" "),star=!sql.contains("*")? 32000:sql.indexOf("*");
        
        int min=comma;
        min=Math.min(min, period);
        min=Math.min(min, space);
        min=Math.min(min, star);
        System.out.println(min);
        return min==32000? sql.length(): (min==0? 1: min);
    }
    
}
