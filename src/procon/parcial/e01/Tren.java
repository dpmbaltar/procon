package procon.parcial.e01;

import java.util.Random;
import procon.parcial.e01.lock.TramoCompartido;

/**
 * Representa un tren.
 */
public class Tren implements Runnable {

    /**
     * El tramo de donde viene el tren (A o B).
     */
    private char tramo;

    /**
     * El nombre del tren.
     */
    private String nombre;

    /**
     * El tramo compartido por donde pasará el tren de forma sincronizada.
     */
    private final TramoCompartido tramoCompartido;

    /**
     * Constructor.
     * @param tc el tramo compartido
     */
    public Tren(TramoCompartido tc) {
        tramoCompartido = tc;
    }

    /**
     * Pasa por el tramo compartido, con una cierta demora.
     */
    @Override
    public void run() {
        try {
            int demora = (new Random()).nextInt(10) * 100;
            tramoCompartido.entrar(this);
            Thread.sleep(demora);
            tramoCompartido.salir();
        } catch (InterruptedException e) {}
    }

    /**
     * Devuelve el tramo de donde viene el tren (A o B).
     * @return
     */
    public char getTramo() {
        return tramo;
    }

    /**
     * Establece el tramo de donde viene el tren (A o B).
     * @param tramo
     */
    public void setTramo(char tramo) {
        this.tramo = tramo;
    }

    /**
     * Devuelve el nombre del tren.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del tren.
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Determina si el tren dado es el mismo tren.
     * @param t
     * @return
     */
    public boolean equals(Tren t) {
        return nombre.equals(t.getNombre()) && tramo == t.getTramo();
    }
}
