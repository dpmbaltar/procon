package procon.tp02.e02;

import java.util.concurrent.Semaphore;

public class Pantalla {

    private final Semaphore semA = new Semaphore(1, true);
    private final Semaphore semB = new Semaphore(0, true);
    private final Semaphore semC = new Semaphore(0, true);
    private int cantB = 0;
    private int cantC = 0;

    public void mostrarA() throws InterruptedException {
        semA.acquire();
        System.out.print('A');
        semB.release();
    }

    public void mostrarB() throws InterruptedException {
        semB.acquire();
        System.out.print('B');
        cantB++;
        if (cantB >= 2) {
            cantB = 0;
            semC.release();
        } else {
            semB.release();
        }
    }

    public void mostrarC() throws InterruptedException {
        semC.acquire();
        System.out.print('C');
        cantC++;
        if (cantC >= 3) {
            cantC = 0;
            semA.release();
        } else {
            semC.release();
        }
    }
}
