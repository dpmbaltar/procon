/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.ArrayDeque;

/**
 * Restaurante familiar.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Restaurante {

	private ArrayDeque clientes;
	
	public static void main(String[] args) {
		Ventana ventana = new Ventana();
		
		Thread chef = new Thread(new Chef(ventana), "Chef");
		Thread mozo = new Thread(new Mozo(ventana), "Mozo");
		
		chef.start();
		mozo.start();
	}
}
