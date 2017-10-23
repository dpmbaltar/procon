/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.ArrayDeque;
import java.util.Random;

/**
 * Mozo.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Mozo implements Runnable {

	/**
	 * Ventana de comunicación chef-mozo.
	 */
	private Ventana ventana;
	
	/**
	 * Pedidos de los clientes.
	 */
	private ArrayDeque<Integer> pedidos;
	
	/**
	 * Constructor.
	 * @param ventana la ventana pasa comidas
	 * @param pedidos los pedidos de los clientes
	 */
	public Mozo(Ventana ventana, ArrayDeque<Integer> pedidos) {
		this.ventana = ventana;
		this.pedidos = pedidos;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				atender();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void atender() throws InterruptedException {
		Random r = new Random();
		ventana.solicitar(r.nextInt(50)+1);
		ventana.servir();
	}
}
