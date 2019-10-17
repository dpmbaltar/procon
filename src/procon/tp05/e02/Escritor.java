package procon.tp05.e02;

import java.util.concurrent.ThreadLocalRandom;

public class Escritor implements Runnable {

    private final Libro recurso;

    public Escritor(Libro recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            while (true) {
                recurso.comenzarEscritura();
                Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
                recurso.terminarEscritura();
                //Thread.yield();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
