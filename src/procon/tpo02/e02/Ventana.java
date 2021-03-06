/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ventana pasa comidas.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Ventana {

    /**
     * Lock de la ventana.
     */
    private final Lock cerrojo = new ReentrantLock(true);

    /**
     * Indica (al Mozo) que un pedido esta listo para ser servido al cliente.
     */
    private final Condition pedidoListo = cerrojo.newCondition();

    /**
     * Indica (al Chef) que hay un nuevo pedido.
     */
    private final Condition pedidoNuevo = cerrojo.newCondition();

    /**
     * Número de pedido en proceso; cuando es 0 indica que no hay pedido en proceso.
     */
    private int pedido = 0;

    /**
     * Determina si el pedido está listo, esperando a ser entregado al cliente.
     */
    private boolean listo = false;

    /**
     * Indica si se toman nuevos pedidos.
     */
    private boolean abierto = true;

    /**
     * Constructor.
     */
    public Ventana() {
    }

    /**
     * Cierra la ventana (no se toman más pedidos).
     */
    public void cerrar() {
        cerrojo.lock();
        try {
            abierto = false;
            pedidoNuevo.signal();
            System.out.println("Ventana: No se toman más pedidos");
        } finally {
            cerrojo.unlock();
        }
    }

    /**
     * Solicita (el mozo) un pedido al Chef.
     *
     * @param pedido el número de pedido
     * @throws InterruptedException
     */
    public void solicitar(int pedido) throws InterruptedException {
        cerrojo.lock();
        try {
            if (pedido > 0) {
                // Esperar si ya se solicito un pedido y aún no se terminó de hacer
                while (this.pedido > 0) {
                    System.out.println("Mozo: Esperando que se termine el pedido #" + this.pedido);
                    pedidoListo.await();
                }
                this.pedido = pedido;
                System.out.println("Mozo: Avisar de nuevo pedido #" + pedido);
                pedidoNuevo.signal();
            }
        } finally {
            cerrojo.unlock();
        }
    }

    /**
     * Sirve el pedido al cliente (mozo).
     *
     * @throws InterruptedException
     */
    public void servir() throws InterruptedException {
        cerrojo.lock();
        try {
            // Servir sólo si hay peidos esperando
            if (pedido > 0) {
                while (!listo) {
                    System.out.println("Mozo: Esperando que el pedido este listo #" + pedido);
                    pedidoListo.await();
                }
                System.out.println("Mozo: Servir pedido #" + pedido);
                pedido = 0;
                listo = false;
            }
        } finally {
            cerrojo.unlock();
        }
    }

    /**
     * Chef entrega un pedido listo a la ventana para ser servido.
     * 
     * @param pedido el número de pedido a entragar
     * @throws InterruptedException
     */
    public void entregar(int pedido) throws InterruptedException {
        cerrojo.lock();
        try {
            if (this.pedido > 0) {
                System.out.println("Chef: Entregar pedido #" + this.pedido);
                listo = true;
                pedidoListo.signal();
            }
        } finally {
            cerrojo.unlock();
        }
    }

    /**
     * Chef toma pedido solicitado por el mozo.
     *
     * @return
     * @throws InterruptedException
     */
    public int tomar() throws InterruptedException {
        int pedidoActual = 0;

        cerrojo.lock();
        try {
            // Esperar por nuevos pedidos sólo cuando esta abierto y no hay pedidos en
            // proceso
            while (pedido <= 0 && abierto) {
                System.out.println("Chef: Esperando por un nuevo pedido...");
                pedidoNuevo.await();
            }
            // Tomar pedidos sólo cuando esta abierto
            if (abierto) {
                pedidoActual = pedido;
                System.out.println("Chef: Tomar pedido #" + pedidoActual);
            }
        } finally {
            cerrojo.unlock();
        }

        return pedidoActual;
    }
}
