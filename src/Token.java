public class Token {

    private boolean reservada;
    private String token;

    public Token(String t,boolean r){
        reservada=r;
        token=t;
    }

    public String getToken() {
        return token;
    }

    public boolean isReservada() {
        return reservada;
    }
}
