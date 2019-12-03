package resources;

import Analizadores.Lexico;
import Archivos.LeerArchivo;
import java.io.IOException;
import java.util.ArrayList;

public class AppCompilador {

    private static Lexico analizadorLexico;
    private static ArrayList<Token> tokens;
    private static ArrayList<String> tablaDeSimbolos;

    public static void main(String [] args) throws IOException {
        LeerArchivo arch=new LeerArchivo("Prueba");
        analizadorLexico=new Lexico();
        tokens=new ArrayList<>();
        tablaDeSimbolos=new ArrayList<>();
        String linea="";
        int l=0;
        while(linea!=null) {
            l++;
            linea = arch.leerSigLinea();
            analizadorLexico.analizarLinea(linea,l,0);
        }

//        for (Token token : tokens) {
//            System.out.println(token);
//        }
    }

    public static void agregarToken(String palabra,boolean pres,String tipo){
        tokens.add(new Token(palabra,pres,tipo));
    }
}
