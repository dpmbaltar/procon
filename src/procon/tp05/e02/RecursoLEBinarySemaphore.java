package procon.tp05.e02;

import java.util.concurrent.Semaphore;

public class RecursoLEBinarySemaphore implements RecursoLE {

    private final Semaphore escritura = new Semaphore(1);
    private final Semaphore lectura = new Semaphore(0);
    private int leyendo = 0;
    private boolean escrito = false;

    @Override
    public void comenzarEscritura() throws InterruptedException {
        escritura.acquire();
        System.out.println(
                Thread.currentThread().getName() + ">>> comienza escritura");
    }

    @Override
    public void comenzarLectura() throws InterruptedException {
        lectura.acquire();
        if (leyendo == 0)
            escritura.acquire();
        leyendo++;
        System.out.println(
                Thread.currentThread().getName() + ">>> comienza lectura");
        lectura.release();
    }

    @Override
    public void terminarEscritura() throws InterruptedException {
        if (!escrito) {
            escrito = true;
            lectura.release();
        }
        System.out.println(
                Thread.currentThread().getName() + ">>> termina escritura");
        escritura.release();
    }

    @Override
    public void terminarLectura() throws InterruptedException {
        lectura.acquire();
        System.out.println(
                Thread.currentThread().getName() + ">>> termina lectura");
        leyendo--;
        if (leyendo == 0)
            escritura.release();
        lectura.release();
    }

}
