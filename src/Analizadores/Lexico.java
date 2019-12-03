package Analizadores;
import resources.AppCompilador;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexico {
    private ArrayList<String> PalabrasReservadas;
    private String[] tiposDeTokens={"modifier","identifier","type","relational operator","aritmetical operator",
                                    "boolean literal","integer literal","open key",";","=","open parentheses","close key",
                                    "close parentheses"};

    public Lexico(){
        PalabrasReservadas=new ArrayList<>();
        llenarArray();
    }

    //Crea los tokens y muestra errores de una linea del archivo
    public void analizarLinea(String linea,int l,int c){
        while (linea!=null && linea.length() > 0) {
            int corte = linea.indexOf(" ");
            String palabra;
            if (corte == -1) {
                palabra=linea;
                if(palabra.indexOf("{")>=0 || palabra.indexOf("=")>=0 ||
                        palabra.indexOf(";")>=0 || palabra.indexOf(">")>=0 || palabra.indexOf("<")>=0){
                    separaTokens(palabra);
                    linea = "";
                    continue;
                }
                linea = "";
            }else {
                palabra = linea.substring(0, corte);
                if(palabra.indexOf("{")>=0 || palabra.indexOf("=")>=0 ||
                        palabra.indexOf(";")>=0 || palabra.indexOf(">")>=0 || palabra.indexOf("<")>=0){
                    separaTokens(palabra);
                    linea = linea.substring(corte + 1);
                    continue;
                }
                if(palabra.equals("")) {
                    linea = linea.substring(corte + 1);
                    continue;
                }
                linea = linea.substring(corte + 1);
            }
            boolean pres = comprobarPalabra(palabra);
            String tipo = tipoDeToken(palabra);
            if (tipo.equals("Error")){
                mostrarError(palabra.charAt(0),l,c+1);
                continue;
            }
            c=c+corte+1;
            AppCompilador.agregarToken(palabra,pres,tipo);
        }
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

    //Retorna el tipo de token en base a la palabra
    public String tipoDeToken(String palabra){
        if (palabra.equals("("))
            return tiposDeTokens[10];
        if (palabra.equals(")"))
            return tiposDeTokens[12];
        if(palabra.equals("="))
            return tiposDeTokens[9];
        if(palabra.equals(";"))
            return tiposDeTokens[8];
        if (palabra.equals("{"))
            return tiposDeTokens[7];
        if(palabra.equals("}"))
            return tiposDeTokens[11];
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
        if(validarExpresion(palabra))
            return tiposDeTokens[1];
        return "Error";
    }

    //Primer llamado de la busqueda binaria
    public boolean comprobarPalabra(String palabra){
        return busquedaBinaria(palabra,0,PalabrasReservadas.size());
    }

    //Busqueda para verificar palabra reservada
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

    //Muestra errores léxicos
    public void mostrarError(char token,int linea,int columna){
        System.out.println("\u001B[31m"+"Error en la línea "+linea+",columna "+columna+", "+token+" es un simbolo no permitido"+"\u001B[0m");
    }

    //Valida que el identifier cumpla con la expresion
    public boolean validarExpresion(String palabra) {
        Pattern pat = Pattern.compile("[A-Za-z0-9]");
        Matcher mat = pat.matcher(palabra);
        if(mat.find())
            return true;
        return false;
    }

    //Separa tokens sin espacios entre ellos
    public void separaTokens(String palabra){
        int i;
        String t;
        while(palabra.length()!=0) {
            if(palabra.charAt(0)=='(' || palabra.charAt(0)==')'){
                AppCompilador.agregarToken(palabra.charAt(0)+"",false,tipoDeToken(palabra.charAt(0)+""));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.indexOf("{")==0 || palabra.indexOf("}")==0){
                AppCompilador.agregarToken(palabra.substring(0,1),false,tipoDeToken(palabra.charAt(0)+""));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.indexOf(";")==0){
                AppCompilador.agregarToken(";",false,";");
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(
                    (palabra.charAt(0)=='=' || palabra.charAt(0)=='>' || palabra.charAt(0)=='<')
                            &&
                            (palabra.charAt(1)=='=' || palabra.charAt(1)=='>' || palabra.charAt(1)=='<')
            ){
                String aux=palabra.substring(0,2);
                AppCompilador.agregarToken(aux,false,tipoDeToken(aux));
                if (palabra.length()!=2) {
                    palabra=palabra.substring(2);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.charAt(0)=='='){
                AppCompilador.agregarToken(palabra.charAt(0)+"",false,tipoDeToken(palabra.charAt(0)+""));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            for (i = 0; validarExpresion(palabra.charAt(i)); i++) ;
            t = palabra.substring(0, i);
            boolean pres = comprobarPalabra(t);
            String tipo = tipoDeToken(t);
            AppCompilador.agregarToken(t, pres, tipo);
            palabra = palabra.substring(i);
        }
    }

    //Valida que sean Letras de la a-z mayusculas o minusculas o numeros del 0-9
    public static boolean validarExpresion(char c) {
        Pattern pat = Pattern.compile("[A-Za-z0-9]");
        Matcher mat = pat.matcher(c+"");
        if(mat.find())
            return true;
        return false;
    }
}
