/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 01
 */
package procon.tpo02.e01;

import java.util.ArrayDeque;
import java.util.Random;

public class ProductorConsumidor {

	public static final int LIMITE = 100;
	private ArrayDeque datos;
	private MonitorSemaf mutex;
	private MonitorSemaf vacio;
	private MonitorSemaf lleno;
	
	public static void main(String[] args) {
		ProductorConsumidor pc = new ProductorConsumidor();
		pc.iniciar();
	}

	/**
	 * Constructor.
	 */
	public ProductorConsumidor() {
		datos = new ArrayDeque<Integer>(LIMITE);
		mutex = new MonitorSemaf(1);
		vacio = new MonitorSemaf(LIMITE);
		lleno = new MonitorSemaf(0);
	}
	
	public void iniciar() {
		Thread productor = new Thread(new Productor(), "Productor");
		Thread consumidor = new Thread(new Consumidor(), "Consumidor");
		
		try {
			productor.start();
			consumidor.start();
			productor.join();
			consumidor.join();
		} catch (InterruptedException e) {
			System.out.println("Interrupción");
		}
		
		System.out.println("Terminado");
	}
	
	private class Productor implements Runnable {

		@Override
		public void run() {
			Random generadorDatos = new Random();
			while (true) {
				ponerDato(generadorDatos.nextInt(100));
			}
		}
		
		private void ponerDato(int dato) {
			//TODO
		}
	}
	
	private class Consumidor implements Runnable {

		@Override
		public void run() {
			while (true) {
				System.out.println("Dato consumido: "+sacarDato());
			}
		}
		
		private int sacarDato() {
			//TODO
			return 0;
		}
	}
}
