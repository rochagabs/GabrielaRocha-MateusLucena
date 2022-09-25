package src.game;
public class Diploma {
    private int numero;
    private String dica;

    public Diploma(int numero, String dica) {
        this.numero = numero;
        this.dica = dica;
    }

    public int diplomaNum() {
        return numero;
    }
    
    public String getDica() {
        return dica;
    }
}