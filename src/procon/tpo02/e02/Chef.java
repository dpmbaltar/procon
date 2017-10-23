/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

/**
 * Chef.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Chef implements Runnable {

	/**
	 * Ventana de comunicación chef-mozo.
	 */
	private Restaurante restaurante;
	
	/**
	 * Constructor.
	 * @param restaurante el restaurante
	 */
	public Chef(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				preparar();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void preparar() throws InterruptedException {
		int pedido = restaurante.getVentana().tomar();
		Thread.sleep(500);
		restaurante.getVentana().entregar(pedido);
	}
}
