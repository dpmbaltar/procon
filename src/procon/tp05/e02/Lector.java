package procon.tp05.e02;

import java.util.concurrent.ThreadLocalRandom;

public class Lector implements Runnable {

    private final RecursoLE recurso;

    public Lector(RecursoLE recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                recurso.comenzarLectura();
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 100);
                recurso.terminarLectura();
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 100);
                //Thread.yield();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
