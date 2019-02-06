package procon.tp04.e02;

import java.util.concurrent.ThreadLocalRandom;

public class Personas implements Runnable {
    private GestorSala gestorSala;

    public Personas(GestorSala gestorSala) {
        this.gestorSala = gestorSala;
    }

    @Override
    public void run() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            while (true) {
                if (r.nextBoolean()) {
                    gestorSala.entrarSalaJubilado();
                } else {
                    gestorSala.entrarSala();
                }

                Thread.sleep(r.nextInt(1, 5) * 100);
                gestorSala.salirSala();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
