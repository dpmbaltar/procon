/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.ArrayDeque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * Restaurante familiar.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Restaurante {

    /**
     * Limite de pedidos.
     */
    private static final int LIMITE = 50;

    /**
     * Control de acceso a pedidos.
     */
    private final Semaphore mutexPedidos;

    /**
     * Control de pedidos vacios.
     */
    private final Semaphore vacioPedidos;

    /**
     * Control de pedidos tomados.
     */
    private final Semaphore llenoPedidos;

    /**
     * Pedidos de los clientes.
     */
    private final ArrayDeque<Integer> pedidos;

    /**
     * Determina si el restaurante esta abierto.
     */
    private boolean abierto = false;

    /**
     * Recurso compartido entre el Chef y el Mozo.
     */
    private final Ventana ventana;

    /**
     * Restaurante.
     *
     * @param args
     */
    public static void main(String[] args) {
        Restaurante res = new Restaurante();
        res.abrir();
    }

    /**
     * Constructor.
     */
    public Restaurante() {
        ventana = new Ventana();
        pedidos = new ArrayDeque<>(LIMITE);
        mutexPedidos = new Semaphore(1);
        vacioPedidos = new Semaphore(LIMITE);
        llenoPedidos = new Semaphore(0);
    }

    /**
     * Abre/inicia el restaurante por una cierta cantidad de tiempo.
     */
    public void abrir() {
        abierto = true;
        Thread mozo = new Thread(new Mozo(this), "Mozo");
        Thread chef = new Thread(new Chef(this), "Chef");
        Thread clientes = new Thread(new Clientes(this));

        mozo.start();
        chef.start();
        clientes.start();

        Timer tiempo = new Timer(false);
        tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                cerrar();
                tiempo.cancel();
            }
        }, 8000);
    }

    /**
     * Cierra/termina la ejecución.
     */
    public void cerrar() {
        abierto = false;
        ventana.cerrar();
    }

    /**
     * Devuelve verdadero si el restaurante esta abierto.
     *
     * @return
     */
    public boolean estaAbierto() {
        return abierto;
    }

    /**
     * Devuelve una referencia a la ventana del chef-mozo.
     *
     * @return
     */
    public Ventana getVentana() {
        return ventana;
    }

    /**
     * Indica si hay pedidos pendientes.
     *
     * @return
     */
    public boolean hayPedidos() {
        boolean hayPedidos = false;

        try {
            mutexPedidos.acquire();
            hayPedidos = !pedidos.isEmpty();
            mutexPedidos.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return hayPedidos;
    }

    /**
     * Agrega un pedido de los clientes al estado pendiente.
     *
     * @param pedido
     */
    public void agregarPedido(int pedido) {
        try {
            vacioPedidos.acquire();
            mutexPedidos.acquire();
            pedidos.add(pedido);
            mutexPedidos.release();
            llenoPedidos.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Obtiene un pedido pendiente de los clientes.
     *
     * @return
     */
    public int obtenerPedido() {
        int pedido = 0;
        try {
            System.out.println("Restaurante: Obtener pedido");
            llenoPedidos.acquire();
            mutexPedidos.acquire();
            pedido = pedidos.remove();
            mutexPedidos.release();
            vacioPedidos.release();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Restaurante: Pedido obtenido #" + pedido);

        return pedido;
    }
}
