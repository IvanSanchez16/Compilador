package Analizadores;

import java.util.ArrayList;

public class Sintactico {

    private ArrayList<String> tiposTokens;

    public Sintactico(ArrayList<String> tt){
        tiposTokens=tt;
        analizar();
    }

    //Comienza el analisis sintactico
    private void analizar(){
        for (String token : tiposTokens) {
            
        }
    }
}
