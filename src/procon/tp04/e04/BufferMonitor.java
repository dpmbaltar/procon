package procon.tp04.e04;

import java.util.ArrayDeque;

public class BufferMonitor<E> implements Buffer<E> {

    private static final int CAPACIDAD = 8;
    private final ArrayDeque<E> buffer = new ArrayDeque<>();

    @Override
    public synchronized void agregar(E elemento) throws InterruptedException {
        while (buffer.size() >= CAPACIDAD) {
            System.out.println(">>> Buffer lleno <<<");
            wait();
        }

        buffer.add(elemento);
        notify();
    }

    @Override
    public synchronized E sacar() throws InterruptedException {
        E elemento;
        while (buffer.isEmpty()) {
            System.out.println(">>> Buffer vacío <<<");
            wait();
        }

        elemento = buffer.remove();
        notify();

        return elemento;
    }

}
