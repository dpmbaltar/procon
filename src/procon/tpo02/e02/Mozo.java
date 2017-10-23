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
	}
	
	/**
	 * Atiende los pedidos de los clientes.
	 * @throws InterruptedException
	 */
	public void atender() throws InterruptedException {
		Ventana ventana = restaurante.getVentana();
		ventana.solicitar(restaurante.obtenerPedido());
		ventana.servir();
	}
}
