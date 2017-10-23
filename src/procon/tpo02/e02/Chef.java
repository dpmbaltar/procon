/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Chef.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Chef implements Runnable {

	/**
	 * Restaurante.
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
		while (restaurante.estaAbierto()) {
			try {
				preparar();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void preparar() throws InterruptedException {
		int milis = ThreadLocalRandom.current().nextInt(1, 5) * 100;
		int pedido = restaurante.getVentana().tomar();
		Thread.sleep(milis);
		restaurante.getVentana().entregar(pedido);
	}
}
