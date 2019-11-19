package procon.tpof2019;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Restaurante donde los visitantes almuerzan y/o meriendan.
 * Implementado con Locks.
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

    /**
     * Mutex.
     */
    private final Lock mutex = new ReentrantLock();

    /**
     * Condición para notificar de que hay lugar en restaurante.
     */
    private final Condition hayLugar = mutex.newCondition();

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

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

    /**
     * Visitante entra al restaurante (si hay lugar).
     *
     * @throws InterruptedException
     */
    public void entrar() throws InterruptedException {
        String visitante;

        mutex.lock();
        try {
            // Esperar mientras esté lleno
            while (clientes == capacidad) {
                vista.printRestaurantes(String.format("<<Restaurante %d lleno>>", numero));
                hayLugar.await();
            }

            clientes++;
            visitante = Thread.currentThread().getName();
            vista.printRestaurantes(String.format("%s entra a restaurante %d", visitante, numero));
            vista.agregarClienteRestaurante(numero);
        } finally {
            mutex.unlock();
        }
    }

    /**
     * Visitante sale del restaurante.
     */
    public void salir() {
        String visitante;

        mutex.lock();
        try {
            visitante = Thread.currentThread().getName();
            vista.printRestaurantes(String.format("%s sale de restaurante %d", visitante, numero));
            vista.sacarClienteRestaurante(numero);
            clientes--;
            hayLugar.signalAll();
        } finally {
            mutex.unlock();
        }
    }

}
