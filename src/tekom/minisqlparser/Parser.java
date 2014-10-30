/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tekom.minisqlparser;

import java.util.ArrayList;

/**
 *
 * @author BagusThanatos
 */
public class Parser {
    /*
    Class untuk melakukan parsing terhadap inputan yang diberikan
    */
    private final static ArrayList<String> lexicalName= new ArrayList();
    private final static ArrayList<Integer> lexicalCode= new ArrayList();
    static {
        lexicalName.add("SELECT");
        lexicalName.add("FROM");
        lexicalName.add("TABLE");
        lexicalName.add(".");
        lexicalName.add(",");
        lexicalName.add("*");
        lexicalName.add("AND");
        lexicalName.add("OR");
        for (int i=1;i<=8;i++) lexicalCode.add(i);
    }
    
    private final static int UNIDENTIFIED=0;
    private final static int SELECT=1;
    private final static int FROM=2;
    private final static int TABLE=3;
    private final static int PERIOD=4;
    private final static int COMMA=5;
    private final static int STAR=6;
    private final static int AND=7;
    private final static int OR=8;
    private final static int VARIABLE=9;
    
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
