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
        Thread p;
        try {
            while (true) {
                p = new Thread(new Persona(gestorSala, r.nextBoolean()));
                p.start();
                Thread.sleep(r.nextInt(1, 3) * 10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
