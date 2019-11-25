import Analizador.Lexico;
import Archivos.LeerArchivo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Lexico analizadorLexico;
    private static ArrayList<Token> tokens;

    public static void main(String [] args) throws IOException {
        LeerArchivo arch=new LeerArchivo("Prueba");
        analizadorLexico=new Lexico();
        tokens=new ArrayList<>();
        String linea="",palabra,tipo;
        boolean pres;
        while(linea!=null) {
            linea = arch.leerSigLinea();
            while (linea!=null && linea.length() > 0) {
                int corte = linea.indexOf(" ");
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
                pres=analizadorLexico.comprobarPalabra(palabra);
                tipo=analizadorLexico.tipoDeToken(palabra);
                tokens.add(new Token(palabra,pres,tipo));
            }
        }
        for (Token t:tokens) {
            System.out.println("Token: "+t.getToken()+"\tReservada: "+t.isReservada()+"\tTipo: "+t.getTipo());
        }
    }

    public static void separaTokens(String palabra){
        int i;
        String t;
        while(palabra.length()!=0) {
            if(palabra.charAt(0)=='(' || palabra.charAt(0)==')'){
                tokens.add(new Token(palabra.charAt(0)+"",false,analizadorLexico.tipoDeToken(palabra.charAt(0)+"")));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.indexOf("{")==0 || palabra.indexOf("}")==0){
                tokens.add(new Token(palabra.substring(0,1),false,analizadorLexico.tipoDeToken(palabra.charAt(0)+"")));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.indexOf(";")==0){
                tokens.add(new Token(";",false,";"));
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
                tokens.add(new Token(aux,false,analizadorLexico.tipoDeToken(aux)));
                if (palabra.length()!=2) {
                    palabra=palabra.substring(2);
                    continue;
                }
                palabra="";
                continue;
            }
            if(palabra.charAt(0)=='='){
                tokens.add(new Token(palabra.charAt(0)+"",false,analizadorLexico.tipoDeToken(palabra.charAt(0)+"")));
                if (palabra.length()!=1) {
                    palabra=palabra.substring(1);
                    continue;
                }
                palabra="";
                continue;
            }
            for (i = 0; validarExpresion(palabra.charAt(i)); i++) ;
            t = palabra.substring(0, i);
            boolean pres = analizadorLexico.comprobarPalabra(t);
            String tipo = analizadorLexico.tipoDeToken(t);
            tokens.add(new Token(t, pres, tipo));
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
