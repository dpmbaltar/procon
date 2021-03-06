/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Simula al Chef.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Chef implements Runnable {

    /**
     * Restaurante.
     */
    private final Restaurante restaurante;

    /**
     * Constructor.
     *
     * @param restaurante el restaurante
     */
    public Chef(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Prepara pedidos solicitados por el Mozo, siempre que esté abierto o
     * queden pedidos pendientes.
     */
    @Override
    public void run() {
        while (restaurante.estaAbierto() || restaurante.hayPedidos()) {
            try {
                preparar();
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        System.out.println("Chef: Terminado");
    }

    /**
     * Prepara la comida por una cantidad variable de tiempo y la entrega a la
     * ventana para ser servida por el mozo.
     *
     * @throws InterruptedException
     */
    public void preparar() throws InterruptedException {
        Ventana ventana = restaurante.getVentana();
        int pedido = ventana.tomar();
        int demora = ThreadLocalRandom.current().nextInt(1, 5) * 100;
        Thread.sleep(demora);
        ventana.entregar(pedido);
    }
}
