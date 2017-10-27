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
    private Restaurante restaurante;

    /**
     * Constructor.
     *
     * @param restaurante el restaurante
     */
    public Mozo(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        while (restaurante.estaAbierto() || restaurante.hayPedidos()) {
            try {
                atender();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Mozo: terminado");
    }

    /**
     * Atiende los pedidos de los clientes.
     *
     * @throws InterruptedException
     */
    public void atender() throws InterruptedException {
        int pedido;
        Ventana ventana = restaurante.getVentana();
        pedido = restaurante.obtenerPedido();
        System.out.println("Mozo: Obteniendo pedido #" + pedido);
        ventana.solicitar(pedido);
        System.out.println("Mozo: Sirviendo pedido");
        ventana.servir();
    }
}
