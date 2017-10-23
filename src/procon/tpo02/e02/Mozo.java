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

	private Ventana ventana;
	
	public Mozo(Ventana ventana) {
		this.ventana = ventana;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				atender();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void atender() throws InterruptedException {
		Random r = new Random();
		ventana.solicitar(r.nextInt(50)+1);
		ventana.servir();
	}
}
