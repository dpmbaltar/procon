package procon.tp04.e03;

import java.util.Random;

import procon.tp04.e03.Impresora.Tipo;

public class Impresion {

    private static int idSiguiente = 1;
    private final int id;
    private Tipo tipo;

    private static synchronized int idSiguiente() {
        return idSiguiente++;
    }

    public static Impresion aleatoria() {
        Tipo tipo;
        Random r = new Random();
        int i = r.nextInt(3);

        if (i == 0) {
            tipo = Tipo.A;
        } else if (i == 1) {
            tipo = Tipo.B;
        } else {
            tipo = Tipo.CUALQUIERA;
        }

        return new Impresion(tipo);
    }

    public Impresion() {
        this(null);
    }

    public Impresion(Tipo tipo) {
        this.id = idSiguiente();
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean equals(Impresion i) {
        return id == i.getId();
    }

    @Override
    public String toString() {
        return String.format("Impresion [tipo=%s,id=%d]", tipo, id);
    }
}
