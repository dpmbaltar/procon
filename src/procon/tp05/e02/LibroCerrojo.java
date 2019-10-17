package procon.tp05.e02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LibroCerrojo implements Libro {

    private int cantidadLeyendo = 0;
    private int cantidadEscribiendo = 0;
    private boolean escrito = false;

    private final Lock mutex = new ReentrantLock();
    private final Condition leer = mutex.newCondition();
    private final Condition escribir = mutex.newCondition();

    @Override
    public void comenzarEscritura() throws InterruptedException {
        mutex.lock();
        try {
            while (cantidadLeyendo > 0 || cantidadEscribiendo > 0) {
                escribir.await();
            }

            cantidadEscribiendo++;
            System.out.println(Thread.currentThread().getName() + "> comienza escritura");
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void comenzarLectura() throws InterruptedException {
        mutex.lock();
        try {
            while (cantidadEscribiendo > 0 || !escrito) {
                leer.await();
            }

            cantidadLeyendo++;
            System.out.println(Thread.currentThread().getName() + "> comienza lectura ");
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void terminarEscritura() {
        mutex.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "> termina escritura");
            cantidadEscribiendo--;

            if (!escrito) {
                escrito = true;
            }

            escribir.signalAll();
            leer.signalAll();
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public void terminarLectura() throws InterruptedException {
        mutex.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "> termina lectura");
            cantidadLeyendo--;
            escribir.signalAll();
            leer.signalAll();
        } finally {
            mutex.unlock();
        }
    }

}
