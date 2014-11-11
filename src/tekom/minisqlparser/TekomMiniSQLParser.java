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
        //String a="SELECT * FROM tab_mhs WHERE tab_mhs.nim = “110399999” and nama like \"a%\" and ();";
        String a = "select * from coba where a>= b a<b";
        Parser.parseSQL2(a).stream().forEach((l) -> {
            System.out.println(l.getValue()+" | "+l.getName()+" | "+l.getTokenCode());
        });
    }
    
}
