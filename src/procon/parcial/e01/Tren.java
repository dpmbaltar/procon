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

    public char getTramo() {
        return tramo;
    }

    public void setTramo(char tramo) {
        this.tramo = tramo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
