package procon.tp04.e04;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

public class BufferUnlimitedSemaphore<E> implements Buffer<E> {

    private final ArrayDeque<E> buffer = new ArrayDeque<>();
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore puedeSacar = new Semaphore(0);

    @Override
    public void agregar(E elemento) throws InterruptedException {
        mutex.acquire();
        buffer.add(elemento);
        if (buffer.size() == 1)
            puedeSacar.release();
        mutex.release();
    }

    @Override
    public E sacar() throws InterruptedException {
        E elemento = null;
        puedeSacar.acquire();
        mutex.acquire();
        elemento = buffer.remove();
        if (buffer.size() > 0)
            puedeSacar.release();
        mutex.release();

        return elemento;
    }

}
