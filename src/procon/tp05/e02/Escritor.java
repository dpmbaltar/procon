package procon.tp05.e02;

import java.util.concurrent.ThreadLocalRandom;

public class Escritor implements Runnable {

    private final RecursoLE recurso;

    public Escritor(RecursoLE recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                recurso.comenzarEscritura();
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 100);
                recurso.terminarEscritura();
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 100);
                //Thread.yield();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
