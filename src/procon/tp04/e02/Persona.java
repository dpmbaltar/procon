package procon.tp04.e02;

import java.util.concurrent.ThreadLocalRandom;

public class Persona implements Runnable {
    private GestorSala gestorSala;
    private boolean esJubilado;

    public Persona(GestorSala gestorSala, boolean esJubilado) {
        this.gestorSala = gestorSala;
        this.esJubilado = esJubilado;
    }

    @Override
    public void run() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            if (esJubilado) {
                gestorSala.entrarSalaJubilado();
            } else {
                gestorSala.entrarSala();
            }

            Thread.sleep(r.nextInt(1, 5) * 1000);
            gestorSala.salirSala();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
