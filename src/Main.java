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
        String linea="",palabra;
        while(linea!=null) {
            linea = arch.leerSigLinea();
            while (linea.length() > 0) {
                int corte = linea.indexOf(" ");
                if (corte == -1) {
                    palabra=linea;
                    linea = "";
                }
                palabra=linea.substring(0, corte);
                linea = linea.substring(corte + 1);
                if(!analizadorLexico.comprobarPalabra(palabra)) {
                    tokens.add(new Token(palabra, false));
                }else{
                    tokens.add(new Token(palabra,true));
                }
            }
        }
    }
}
