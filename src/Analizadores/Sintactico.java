package Analizadores;

import java.util.ArrayList;

public class Sintactico {

    private ArrayList<String> tiposTokens;
    private int contador=0;
    private boolean bandera;

    public Sintactico(ArrayList<String> tt){
        tiposTokens=tt;
        analizar();
    }

    //Comienza el analisis sintactico
    private void analizar(){

            if(AnalizarClase(bandera)) {
                System.out.println("La clase esta bien declarada");
            }else{
                System.out.println("ERROR AL CREAR LA CLASE");
            }


    }

    public boolean AnalizarClase(boolean bandera) {
        if (tiposTokens.get(contador).equals("modifier"))
            contador++;
        if (tiposTokens.get(contador).equals("class")) {
            contador++;
            if (tiposTokens.get(contador).equals("identifier")) {
                contador++;
                if (tiposTokens.get(contador).equals("open key")) {
                    contador++;
                }
                    if (AnalizarFieldDeclaracion(bandera)) {
                        if (AnalizarStatement(bandera)) {
                        }
                        if (AnalizarIfStatement(bandera)) {
                        }
                        if(AnalizarWhileStatement(bandera)){
                        }
                    }
                if (tiposTokens.get(contador).equals("close key")) {
                        bandera = true;
                    }
            }
        }else{
            bandera= false;
        }
        return bandera;
    }


    public boolean AnalizarFieldDeclaracion(boolean bandera) {
        if (AnalizarDeclaracionVariable(bandera)) {
            contador++;
            if (tiposTokens.get(contador).equals(";")) {
                contador++;
                bandera= true;
            } else
                bandera= false;
        }
        return bandera;
    }

    public boolean AnalizarDeclaracionVariable(boolean bandera) {
        if(tiposTokens.get(contador).equals("modifier"))
            contador++;
        if (tiposTokens.get(contador).equals("type")) {
            contador++;
            if (AnalizarDeclaradorVariable(bandera) || tiposTokens.get(contador).equals("identifier")) {
                bandera= true;
            }else {
                bandera= false;
            }
        } else {
            bandera= false;
        }
        return bandera;
    }

    public boolean AnalizarDeclaradorVariable(boolean bandera){
        int c= contador;
        if(tiposTokens.get(contador).equals("identifier")){
            contador++;
            if(tiposTokens.get(contador).equals("=")) {
                contador++;
                if (tiposTokens.get(contador).equals("integer literal") || tiposTokens.get(contador).equals("boolean literal")) {
                    bandera = true;
                }
            }else{
                contador=c;
                bandera=false;
            }
        }else{
            bandera=false;
        }
        return bandera;
    }

    public boolean AnalizarDeclaradorV(boolean bandera) {
        int c = contador;
        if (tiposTokens.get(contador).equals("identifier")) {
            contador++;
            if (tiposTokens.get(contador).equals("=")) {
                contador++;
                if (tiposTokens.get(contador).equals("integer literal") || tiposTokens.get(contador).equals("boolean literal")) {
                    contador++;
                    if (tiposTokens.get(contador).equals(";")) {
                        bandera = true;
                    }
                } else {
                    contador = c;
                    bandera = false;
                }
            } else {
                bandera = false;
            }
        } return bandera;
    }

    public boolean AnalizarExpresion(boolean bandera){
        if(AnalizarTestingExpresion(bandera)) {
            bandera= true;
        }else {
            bandera = false;
        }
        return bandera;
    }

    public boolean AnalizarTestingExpresion(boolean bandera){
        if(tiposTokens.get(contador).equals("integer literal")|| tiposTokens.get(contador).equals("identifier")){
            contador++;
            if(tiposTokens.get(contador).equals("relational operator")){
                contador++;
                if(tiposTokens.get(contador).equals("integer literal")||tiposTokens.get(contador).equals("identifier")){
                    bandera=true;
                }else{
                    bandera= false;
                }
            }else{
                bandera= false;
            }
        }
        return bandera;
    }

    public boolean AnalizarStatement(boolean bandera){
        if(AnalizarFieldDeclaracion(bandera) || tiposTokens.get(contador).equals("if")
                || tiposTokens.get(contador).equals("while")){
            bandera= true;
        }else{
             bandera= false;
        }
        return bandera;
    }

    public boolean AnalizarWhileStatement(boolean bandera) {
        if (tiposTokens.get(contador).equals("while")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                if (AnalizarExpresion(bandera)) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        if (tiposTokens.get(contador).equals("open key")) {
                            contador++;
                            if (AnalizarDeclaradorV(bandera)){
                                contador++;
                                if (tiposTokens.get(contador).equals("close key")) {
                                    bandera= true;
                                }
                            }

                        }
                    }
                }
            }
        } return bandera;
    }

    public boolean AnalizarIfStatement(boolean bandera) {
        if (tiposTokens.get(contador).equals("if")) {
            contador++;
            if (tiposTokens.get(contador).equals("open parentheses")) {
                contador++;
                if (AnalizarExpresion(bandera)) {
                    contador++;
                    if (tiposTokens.get(contador).equals("close parentheses")) {
                        contador++;
                        if (tiposTokens.get(contador).equals("open key")) {
                            contador++;
                            if (AnalizarDeclaradorV(bandera)){
                                contador++;
                                if (tiposTokens.get(contador).equals("close key")) {
                                    bandera= true;
                                }
                            }

                        }
                    }
                }
            }
        } return bandera;
    }

    public boolean AnalizarType(boolean bandera){
        if(AnalizarTypeSpecifier(bandera)){
            return bandera= true;
        } else return bandera= false;
    }

    public boolean AnalizarTypeSpecifier(boolean bandera){
        if(tiposTokens.get(contador).equals("boolean literal")|| tiposTokens.get(contador).equals("integer literal")){
            return bandera=true;
        } else return bandera=false;
    }

    public boolean AnalizarExpresionArtimetica(boolean bandera){
        if(tiposTokens.get(contador).equals("identifier")){
            contador++;
            if(tiposTokens.get(contador).equals("=")){
                contador++;
                if(tiposTokens.get(contador).equals("integer literal")){
                    contador++;
                    if(tiposTokens.get(contador).equals("aritmetic operator")){
                        contador++;
                        if(tiposTokens.get(contador).equals("integer literal")){
                            contador++;
                            if(tiposTokens.get(contador).equals(";")){
                                return bandera=true;
                            }
                        } else {
                            return bandera=false;
                        }
                    } else {
                        return bandera=false;
                    }
                } else {
                    return bandera=false;
                }
            } else {
                return bandera=false;
            }
        } else {
            return bandera=false;
        }
        return bandera;
    }
}
