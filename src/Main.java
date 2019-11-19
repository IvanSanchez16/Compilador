import Archivos.LeerArchivo;

public class Main {

    public static void main(String [] args){
        LeerArchivo arch=new LeerArchivo("Prueba");
        for (int i=0;i<6;i++) {
            try {
                System.out.println(arch.leerSigLinea());
            } catch (Exception e) {
                break;
            }
        }
        System.out.println("Termino");
    }
}
