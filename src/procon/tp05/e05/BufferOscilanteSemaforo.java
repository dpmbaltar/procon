package procon.tp05.e05;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BufferOscilanteSemaforo implements BufferOscilante {

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore insertar = new Semaphore(1);
    private final Semaphore extraer = new Semaphore(1);

    private Queue<Object> colaInsertar = new LinkedList<>();
    private Queue<Object> colaExtraer = new LinkedList<>();

    @Override
    public boolean insertar(Object dato) {
        try {
            insertar.acquire();
            System.out.println(Thread.currentThread().getName() + " va a insertar " + String.valueOf(dato));
            colaInsertar.add(dato);
            oscilar();
            System.out.println(Thread.currentThread().getName() + " ha insertado " + String.valueOf(dato));
            insertar.release();
        } catch (InterruptedException e) {}

        return true;
    }

    @Override
    public Object extraer() {
        Object dato = null;

        try {
            extraer.acquire();
            System.out.println(Thread.currentThread().getName() + " va a extraer");
            dato = colaExtraer.poll();
            oscilar();
            System.out.println(Thread.currentThread().getName() + " ha extraido " + String.valueOf(dato));
            extraer.release();
        } catch (InterruptedException e) {}

        return dato;
    }

    private void oscilar() {
        if (mutex.tryAcquire()) {
            if (colaExtraer.isEmpty() && !colaInsertar.isEmpty()) {
                Queue cola = colaExtraer;
                colaExtraer = colaInsertar;
                colaInsertar = cola;
            }

            mutex.release();
        }
    }

}
