package procon.tpof2019;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Restaurante
 * Locks
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Restaurante {

    /**
     * Número del restaurante.
     */
    private final int numero;

    /**
     * Capacidad del restaurante.
     */
    private final int capacidad;

    /**
     * Cantidad de clientes en el restaurante.
     */
    private int clientes = 0;

    private final Lock mutex = new ReentrantLock();
    private final Condition hayLugar = mutex.newCondition();

    private final VistaParque vp = VistaParque.getInstance();

    /**
     * Constructor con el número y la capacidad.
     *
     * @param numero el número del restaurante
     * @param capacidad la capacidad del restaurante
     */
    public Restaurante(int numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
    }

    public void entrar() throws InterruptedException {
        String visitante;

        mutex.lock();

        try {
            // Esperar mientras esté lleno
            while (clientes == capacidad) {
                vp.printRestaurantes(String.format("<<Restaurante %d lleno>>", numero));
                hayLugar.await();
            }

            clientes++;
            visitante = Thread.currentThread().getName();
            vp.printRestaurantes(String.format("%s entra al restaurante %d", visitante, numero));
            vp.agregarCliente(numero);
        } finally {
            mutex.unlock();
        }
    }

    public void salir() {
        String visitante;

        mutex.lock();

        try {
            visitante = Thread.currentThread().getName();
            vp.printRestaurantes(String.format("%s sale del restaurante %d", visitante, numero));
            vp.sacarCliente(numero);
            clientes--;
            hayLugar.signalAll();
        } finally {
            mutex.unlock();
        }
    }

}
