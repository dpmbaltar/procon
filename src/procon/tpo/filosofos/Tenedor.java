package procon.tpo.filosofos;

import java.util.concurrent.Semaphore;

public class Tenedor {

    private int num;
    private final Semaphore sem;

    public Tenedor(int num) {
        this.num = num;
        this.sem = new Semaphore(1, true);
    }

    public void ocupar() throws InterruptedException {
        sem.acquire();
        System.out.println(Thread.currentThread().getName() + " ocupa el tenedor " + num);
    }

    public void liberar() {
        System.out.println(Thread.currentThread().getName() + " libera el tenedor " + num);
        sem.release();
    }

}
