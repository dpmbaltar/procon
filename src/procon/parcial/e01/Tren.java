package procon.parcial.e01;

import java.util.Random;

/**
 * Representa un tren.
 */
public class Tren implements Runnable {

    private char tramo;
    private String nombre;
    private TramoCompartido tramoCompartido;

    public Tren(TramoCompartido tc) {
        tramoCompartido = tc;
    }

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
