package procon.tp04.e04;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

public class BufferSemaphore<E> implements Buffer<E> {

    private static final int CAPACIDAD = 8;
    private final ArrayDeque<E> buffer = new ArrayDeque<>();
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore agregar = new Semaphore(1);
    private final Semaphore sacar = new Semaphore(0);

    @Override
    public void agregar(E elemento) throws InterruptedException {
        agregar.acquire();
        mutex.acquire();
        buffer.add(elemento);
        if (buffer.size() < CAPACIDAD)
            agregar.release();
        sacar.release();
        mutex.release();
    }

    @Override
    public E sacar() throws InterruptedException {
        E elemento;
        sacar.acquire();
        mutex.acquire();
        elemento = buffer.remove();
        if (buffer.size() > 0)
            sacar.release();
        if (buffer.size() == (CAPACIDAD - 1))
            agregar.release();
        mutex.release();

        return elemento;
    }

}
