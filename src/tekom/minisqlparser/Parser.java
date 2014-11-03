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
    private static Stack<String> s = new Stack();
    private final static ArrayList<String> lexicalName= new ArrayList();
    private final static ArrayList<Integer> lexicalCode= new ArrayList();
    static {
        lexicalName.add("SELECT");
        lexicalName.add("*");
        lexicalName.add("WHERE");
        lexicalName.add("FROM");
        lexicalName.add("(");
        lexicalName.add(")");
        lexicalName.add(".");
        lexicalName.add(",");
        lexicalName.add(";");
        lexicalName.add("AND");
        lexicalName.add("OR");
        lexicalName.add("NOT");
        lexicalName.add(">=");
        lexicalName.add("=");
        lexicalName.add("<=");
        lexicalName.add("<");
        lexicalName.add(">");
        lexicalName.add("LIKE");
        lexicalName.add("UNION");
        lexicalName.add("JOIN");
        for (int i=1;i<=20;i++) lexicalCode.add(i);
    }
    
    private final static int UNIDENTIFIED=0;
    private final static int VARIABLE=21;
    private final static int CONSTANT=22;
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
        String logicalString="";
        ArrayList<TokenLexic> result = new ArrayList();
        String temp;
        StringTokenizer st= new StringTokenizer(sql,"*,.<>=(); ",true);
        while(st.hasMoreTokens()){
            temp=st.nextToken();
            if (!temp.equals(" ")){
                if (lexicalName.contains(temp.toUpperCase())) result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(temp.toUpperCase())),temp,temp));
                else if (temp.matches("^[0-9]+$") || temp.contains("\"") || temp.contains("\'")) result.add(new TokenLexic(CONSTANT,TokenLexic.CONSTANT,temp));
                else if (temp.equals("=") || temp.equals(">")|| temp.equals("<")) {
                    if (logical){
                        logical=false;
                        result.remove(result.size()-1);
                        result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(temp.toUpperCase())),temp,temp));
                    }
                    else {
                        logical=true;
                        logicalString=temp;
                    }
                    continue;
                }
                else result.add(new TokenLexic(VARIABLE,temp, temp));
                if (logical) logical=false;
            }
        }
        
        return result;
    }
    public static ArrayList<Integer> parseLexical(String l){
        /*
        melakukan parsing terhadap inputan yang berupa simbol lexical, seperti: 1 2 11
        masing masing simbol dipisahkan spasi
        */
        
        ArrayList result=new ArrayList();
        while(!l.isEmpty()){
            System.out.println(l);
            l=l.trim();
            if (l.isEmpty()) return result;
            result.add(Integer.parseInt(l.substring(0,l.contains(" ") ? l.indexOf(" "): l.length())));
            if (l.contains(" ")) l=l.substring(l.indexOf(" ")+1);
            else l="";
        }
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
