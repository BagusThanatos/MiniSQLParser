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
public class TekomMiniSQLParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String a="select * from bagus.tab,aku.tab";
        ArrayList<Integer> r= Parser.parseSQL(a);
        ArrayList<Integer> r2=Parser.parseLexical(" 2  3    5   7");
        System.out.println(r);
        System.out.println(r2);
    }
    
}
