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
        lexicalName.add("IN");      //10
        lexicalName.add("AND");     //11
        lexicalName.add("OR");      //12
        lexicalName.add("NOT");     //13
        lexicalName.add(">=");      //14
        lexicalName.add("=");       //15
        lexicalName.add("<=");      //16
        lexicalName.add("<");       //17
        lexicalName.add(">");       //18
        lexicalName.add("LIKE");    //19
        lexicalName.add("UNION");   //20
        lexicalName.add("JOIN");    //21
        for (int i=1;i<=21;i++) lexicalCode.add(i);
    }
    public final static int START=0;
    public final static int SELECT=1;
    public final static int STAR=2;
    public final static int WHERE=3;
    public final static int FROM=4;
    public final static int KURKA=5;
    public final static int KURTUP=6;
    public final static int PERIOD=7;
    public final static int COMMA=8;
    public final static int SEMICOLON=9;
    public final static int IN=10;
    public final static int AND=11;
    public final static int OR=12;
    public final static int NOT=13;
    public final static int GREATER_EQUAL=14;
    public final static int EQUAL=15;
    public final static int LESS_EQUAL=16;
    public final static int LESS=17;
    public final static int GREATER=18;
    public final static int LIKE=19;
    public final static int UNION=20;
    public final static int JOIN=21;
    public final static int VARIABLE=22;
    public final static int CONSTANT_STRING=23;
    public final static int CONSTANT_NUMBER=24;
    
    public final static int UNIDENTIFIED=25;
    public final static int KEYWORDS=10;
    public final static int BOOLEANS=12;
    public final static int LOGIC_OPERATORS=18;
    public final static int SET_OPERATOR=20;
    
    
    //ini untuk melakukan pengecekan terhadap kebenaran suatu query
    private final static State s1= new State("q1", false);
    private final static State s2= new State("q2", false);
    private final static State s3= new State("q3", false);
    private final static State s4= new State("q4", false);
    private final static State s5= new State("q5", false);
    private final static State s6= new State("q6", true);
    static {
        s1.insertNextState(s2, SELECT);
        s2.insertNextState(s3, STAR);
        s3.insertNextState(s4, FROM);
        s4.insertNextState(s5, VARIABLE);
        s5.insertNextState(s6, SEMICOLON);
        
    }
    
    
    public static ArrayList<TokenLexic> parseSQL(String sql){
        /*
        akan mengembalikan bentuk berupa kode lexical dari inputan String sql yang ada
        */
        ArrayList<TokenLexic> result= new ArrayList();
        sql=sql.replace("\n", " ");
        sql=sql.replace("\t", " ");
        int flag=START;
        String temp="";
        String state="";
        char tempChar;
        for (int i =0; i< sql.length();i++){
            tempChar=sql.charAt(i);
            switch(flag) {
                case SELECT:
                    state="SELECT";
                    break;
                case JOIN:
                    state="JOIN";
                    break;
                case FROM:
                    state="FROM";
                    break;
                case WHERE:
                    state="WHERE";
                    break;
                case IN :
                    state="IN";
                    break;
                case AND:
                    state="AND";
                    break;
                case OR:
                    state="OR";
                    break;
                case NOT:
                    state="NOT";
                    break;
                case UNION:
                    state="UNION";
                    break;
                case LIKE:
                    state="LIKE";
                    break;
                default:
                     ;  
            }
            if (flag==START) {
                if (tempChar=='s' || tempChar==('S')) flag=SELECT;
                else if (tempChar=='j' || tempChar=='J') flag=JOIN;
                else if (tempChar=='a' || tempChar=='A') flag =AND;
                else if (tempChar=='o' || tempChar=='O') flag =OR;
                else if (tempChar=='n' || tempChar=='N') flag =NOT;
                else if (tempChar=='w' || tempChar=='W') flag=WHERE;
                else if (tempChar=='f' || tempChar=='F') flag =FROM;
                else if (tempChar=='u' || tempChar=='U') flag =UNION;
                else if (tempChar=='l' || tempChar=='L') flag =LIKE;
                else if (tempChar=='i' || tempChar=='I') flag=IN;
                else if (tempChar=='\"') flag = CONSTANT_STRING;
                else if (tempChar>=48 && tempChar<=57) flag=CONSTANT_NUMBER;
                else if (tempChar=='>' || tempChar=='<') flag=EQUAL;
                else if ((tempChar+"").matches("^[*,.=(); ]")) {
                    if (!(tempChar+"").equals(" ")) result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(sql.charAt(i)+"")), "", sql.charAt(i)+""));
                    temp="";
                    continue;
                }
                else flag=VARIABLE;
                temp=tempChar+"";
            }
            else if (flag==EQUAL){
                if (tempChar!='=') {
                    result.add(new TokenLexic(temp.equals("<") ? LESS:GREATER, "", temp));
                    i-=1;
                }
                else if (temp.equals(">")) result.add(new TokenLexic(GREATER_EQUAL, "", ">="));
                else if (temp.equals("<")) result.add(new TokenLexic(LESS_EQUAL, "", "<="));
                System.out.println(temp+tempChar);
                temp="";
                flag=START;
            }
            /*
            else if ((tempChar+"").matches("^[*,.<>=(); ]") && flag!=CONSTANT_NUMBER) {
                if (!temp.equals("")){
                    result.add(new TokenLexic(flag, "", temp));
                    if (!(tempChar+"").equals(" ")) result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(sql.charAt(i)+"")), "", sql.charAt(i)+""));
                    temp="";
                    flag=START;
                }
            }*/
            else if (flag==CONSTANT_STRING) {
                if (tempChar!='\"') temp+=tempChar;
                else {
                    result.add(new TokenLexic(CONSTANT_STRING,"",temp+tempChar));
                    temp="";
                    flag=START;
                }
            }
            else if ((flag==CONSTANT_NUMBER)) {
                if (tempChar>=48 && tempChar<=57) temp+=tempChar;
                else if (tempChar=='.') {
                    if (temp.contains(".")) flag=UNIDENTIFIED;
                    temp+=tempChar;
                }
                else if ((tempChar+"").matches("^[*,.<>=();\" ]")){
                    result.add(new TokenLexic(VARIABLE, "Variable", temp));
                    if (tempChar=='>' || tempChar=='<') {
                        flag=EQUAL;
                        temp=tempChar+"";
                            continue;
                        }
                    else if (tempChar=='\"') {
                        flag=CONSTANT_STRING;
                        temp=tempChar+"";
                        continue;
                    }
                    else if (!(tempChar+"").equals(" ")) result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(sql.charAt(i)+"")), "", sql.charAt(i)+""));
                    flag=START;
                    temp="";
                }
            }
            else if (flag==UNIDENTIFIED){
                temp+=tempChar;
            }
            else if (flag==VARIABLE){
                if ((tempChar+"").matches("^[*,.<>=();\" ]")){
                    result.add(new TokenLexic(VARIABLE, "Variable", temp));
                    if (tempChar=='>' || tempChar=='<') {
                            flag=EQUAL;
                            temp=tempChar+"";
                            continue;
                        }
                    else if (tempChar=='\"') {
                        flag=CONSTANT_STRING;
                        temp=tempChar+"";
                        continue;
                    }
                    else if (!(tempChar+"").equals(" ")) result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(sql.charAt(i)+"")), "", sql.charAt(i)+""));
                    flag=START;
                    temp="";
                }
                else temp+=tempChar;
            }
            else {
                if ((tempChar+"").matches("^[*,.<>=();\" ]")) {
                    if (temp.toUpperCase().equals(state)){
                        result.add(new TokenLexic(flag, "", temp));   
                    }
                    else {
                        result.add(new TokenLexic(VARIABLE, "Variable", temp));
                        if (tempChar=='>' || tempChar=='<') {
                            flag=EQUAL;
                            temp=tempChar+"";
                            continue;
                        }
                        else if (tempChar=='\"') {
                            flag=CONSTANT_STRING;
                            temp=tempChar+"";
                            continue;
                        }
                        else if (!(tempChar+"").equals(" ")) result.add(new TokenLexic(lexicalCode.get(lexicalName.indexOf(sql.charAt(i)+"")), "", sql.charAt(i)+""));
                        
                    }
                    flag=START;
                    temp="";
                }
                else if (state.contains((temp+tempChar).toUpperCase())) temp+=tempChar;
                else {
                    flag=VARIABLE;
                    temp+=tempChar;
                }
            }
            if (temp.equals("<")) System.out.println(flag);
        }
        if (!temp.isEmpty()) result.add(new TokenLexic(flag, "", temp));
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
    public static boolean isValid(ArrayList<Integer> i){
        Stack<Integer> stack = new Stack();
        State s= s1;
        for (Integer i_ : i) {
            s=s.getNextStates(i_, NextState.emptyString);
            if (s==null) return false;
        }
        return s.getStatus()==true && stack.isEmpty();
    }
    public static ArrayList<Integer> toArrayInt(ArrayList<TokenLexic> t){
        ArrayList<Integer> result= new ArrayList();
        t.stream().forEach((t_) -> {
            result.add(t_.getTokenCode());
        });
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
