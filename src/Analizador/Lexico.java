package Analizador;

import java.util.ArrayList;

public class Lexico {
    private ArrayList<String> PalabrasReservadas;
    private String[] tiposDeTokens={"modifier","identifier","type","relational operator","aritmetical operator",
                                    "boolean literal","integer literal","key",";","="};

    public Lexico(){
        PalabrasReservadas=new ArrayList<>();
        llenarArray();
    }

    private void llenarArray(){
        PalabrasReservadas.add("abstract");
        PalabrasReservadas.add("assert");
        PalabrasReservadas.add("boolean");
        PalabrasReservadas.add("break");
        PalabrasReservadas.add("byte");
        PalabrasReservadas.add("case");
        PalabrasReservadas.add("catch");
        PalabrasReservadas.add("char");
        PalabrasReservadas.add("class");
        PalabrasReservadas.add("const");
        PalabrasReservadas.add("continue");
        PalabrasReservadas.add("default");
        PalabrasReservadas.add("do");
        PalabrasReservadas.add("double");
        PalabrasReservadas.add("else");
        PalabrasReservadas.add("enum");
        PalabrasReservadas.add("extends");
        PalabrasReservadas.add("final");
        PalabrasReservadas.add("finally");
        PalabrasReservadas.add("float");
        PalabrasReservadas.add("for");
        PalabrasReservadas.add("goto");
        PalabrasReservadas.add("if");
        PalabrasReservadas.add("implements");
        PalabrasReservadas.add("import");
        PalabrasReservadas.add("instanceof");
        PalabrasReservadas.add("int");
        PalabrasReservadas.add("interface");
        PalabrasReservadas.add("long");
        PalabrasReservadas.add("native");
        PalabrasReservadas.add("new");
        PalabrasReservadas.add("package");
        PalabrasReservadas.add("private");
        PalabrasReservadas.add("protected");
        PalabrasReservadas.add("public");
        PalabrasReservadas.add("return");
        PalabrasReservadas.add("short");
        PalabrasReservadas.add("static");
        PalabrasReservadas.add("strictfp");
        PalabrasReservadas.add("super");
        PalabrasReservadas.add("switch");
        PalabrasReservadas.add("synchronized");
        PalabrasReservadas.add("this");
        PalabrasReservadas.add("throw");
        PalabrasReservadas.add("throws");
        PalabrasReservadas.add("transient");
        PalabrasReservadas.add("try");
        PalabrasReservadas.add("void");
        PalabrasReservadas.add("volatile");
        PalabrasReservadas.add("while");
    }

    public String tipoDeToken(String palabra){
        if(palabra.equals("="))
            return tiposDeTokens[9];
        if(palabra.equals(";"))
            return tiposDeTokens[8];
        if (palabra.equals("{") || palabra.equals("}"))
            return tiposDeTokens[7];
        if(palabra.equals("+") || palabra.equals("-") || palabra.equals("*") || palabra.equals("/"))
            return tiposDeTokens[4];
        if(palabra.equals("public") || palabra.equals("private"))
            return tiposDeTokens[0];
        if(palabra.equals("int") || palabra.equals("boolean"))
            return tiposDeTokens[2];
        if(palabra.equals("<") || palabra.equals("==") || palabra.equals("!=") || palabra.equals(">") || palabra.equals("<=") || palabra.equals(">="))
            return tiposDeTokens[3];
        if(palabra.equals("true") || palabra.equals("false"))
            return tiposDeTokens[5];
        try{
            Integer.parseInt(palabra);
            return tiposDeTokens[6];
        }catch (Exception e){}
        return tiposDeTokens[1];
    }

    public boolean comprobarPalabra(String palabra){
        return busquedaBinaria(palabra,0,PalabrasReservadas.size());
    }

    private boolean busquedaBinaria(String palabra,int izq,int der){
        int medio=(der+izq)/2;
        int band=palabra.compareTo(PalabrasReservadas.get(medio));
        if(band==0)
            return true;
        if(izq==der || (izq==der-1))
            return false;
        if(band>0)
            return busquedaBinaria(palabra,medio,der);
        return busquedaBinaria(palabra,izq,medio);
    }
}
