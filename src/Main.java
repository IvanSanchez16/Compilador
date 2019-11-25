import Analizador.Lexico;
import Archivos.LeerArchivo;
import java.io.IOException;
import java.util.ArrayList;

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
                    linea = "";
                }else {
                    palabra = linea.substring(0, corte);
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
}
