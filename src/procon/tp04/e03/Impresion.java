package procon.tp04.e03;

import procon.tp04.e03.ServicioImpresionLock.Tipo;

public class Impresion {
    private static int idSiguiente = 1;
    private final int id;
    private Tipo tipo;
    
    public Impresion() {
        this(null);
    }

    public Impresion(Tipo tipo) {
        this.id = idSiguiente;
        this.tipo = tipo;
        idSiguiente++;
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
        return String.format("Impresion [id=%d,tipo=%s]", id, tipo);
    }
}
