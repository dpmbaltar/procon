/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

/**
 * Simula al Mozo.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Mozo implements Runnable {

    /**
     * Restaurante.
     */
    private final Restaurante restaurante;

    /**
     * Constructor.
     *
     * @param restaurante el restaurante
     */
    public Mozo(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Atiende los pedidos de los clientes mientras esté abierto, o queden
     * pedidos pendientes.
     */
    @Override
    public void run() {
        while (restaurante.estaAbierto() || restaurante.hayPedidos()) {
            try {
                atender();
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }

        System.out.println("Mozo: Terminado");
    }

    /**
     * Atiende los pedidos de los clientes; solicitando al Chef/sirviendo los
     * pedidos.
     *
     * @throws InterruptedException
     */
    public void atender() throws InterruptedException {
        int pedido;
        Ventana ventana = restaurante.getVentana();
        pedido = restaurante.obtenerPedido();
        ventana.solicitar(pedido);
        ventana.servir();
    }
}
