/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Simula el ingreso de clientes (pedidos) cada cierta cantidad de tiempo.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Clientes implements Runnable {

    /**
     * Restaurante.
     */
    private Restaurante restaurante;

    /**
     * Constructor.
     *
     * @param restaurante el restaurante
     */
    public Clientes(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        int numeroPedido = 1;
        while (restaurante.estaAbierto()) {
            int intervalo = ThreadLocalRandom.current().nextInt(1, 8) * 100;

            try {
                Thread.sleep(intervalo);
            } catch (InterruptedException e) {
            }

            restaurante.agregarPedido(numeroPedido);
            System.out.println("Nuevo cliente para el pedido #" + numeroPedido);
            numeroPedido++;
        }
        
        System.out.println("No entran más clientes");
    }
}
