package procon.parcial.e01;

import java.util.Random;

/**
 * Representa un tren.
 */
public class Tren implements Runnable {

    private int orden;
    private char tramo;
    private String nombre;
    private final TramoCompartido tramoCompartido;

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
            tramoCompartido.entrar(nombre, tramo, orden);
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

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
