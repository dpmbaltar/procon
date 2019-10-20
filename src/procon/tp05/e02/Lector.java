package procon.tp05.e02;

import java.util.concurrent.ThreadLocalRandom;

public class Lector implements Runnable {

    private final Libro recurso;

    public Lector(Libro recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            while (true) {
                recurso.comenzarLectura();
                Thread.sleep(ThreadLocalRandom.current().nextInt(10, 15) * 100);
                recurso.terminarLectura();
                //Thread.sleep(ThreadLocalRandom.current().nextInt(10, 15) * 100);
                //Thread.yield();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
