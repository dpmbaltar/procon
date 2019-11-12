package procon.tpof2019;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Restaurante
 * Locks
 */
public class Restaurante {

    private final int capacidad;
    private int clientes = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition hayLugar = lock.newCondition();

    public Restaurante(int capacidad) {
        this.capacidad = capacidad;
    }

    private void entrar() throws InterruptedException {
        lock.lock();

        try {
            while (clientes == capacidad)
                hayLugar.await();

        } finally {
            lock.unlock();
        }
    }

    private void salir() {

    }

}
