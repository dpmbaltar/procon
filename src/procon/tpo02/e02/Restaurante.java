/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.ArrayDeque;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Restaurante familiar.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Restaurante {

	/**
	 * Determina si el restaurante esta abierto.
	 */
	private boolean abierto = false;
	
	/**
	 * Pedidos de los clientes.
	 */
	private ArrayDeque<Integer> pedidos;
	
	/**
	 * Recurso compartido entre el Chef y el Mozo.
	 */
	private Ventana ventana;
	
	public static void main(String[] args) {
		Restaurante res = new Restaurante();
		res.abrir();
	}
	
	/**
	 * Constructor.
	 */
	public Restaurante() {
		pedidos = new ArrayDeque<Integer>();
		ventana = new Ventana();
	}
	
	/**
	 * Abre/inicia el restaurante por una cierta cantidad de tiempo.
	 */
	public void abrir() {
		Thread chef = new Thread(new Chef(this), "Chef");
		Thread mozo = new Thread(new Mozo(this), "Mozo");
		Thread clientes = new Thread(new Clientes());
		abierto = true;
		
		//chef.start();
		//mozo.start();
		clientes.start();
		
		Timer tiempo = new Timer();
		tiempo.schedule(new TimerTask() {
			@Override
			public void run() {
				abierto = false;
			}
		}, 8000);
	}
	
	/**
	 * Devuelve verdadero si el restaurante esta abierto.
	 * @return
	 */
	public boolean estaAbierto() {
		return abierto;
	}
	
	/**
	 * Devuelve los pedidos sin atender.
	 * @return
	 */
	public ArrayDeque<Integer> getPedidos() {
		return pedidos;
	}
	
	/**
	 * Devuelve una referencia a la ventana del chef-mozo.
	 * @return
	 */
	public Ventana getVentana() {
		return ventana;
	}
	
	/**
	 * Simula el ingreso de clientes (pedidos) cada cierta cantidad de tiempo.
	 */
	private class Clientes implements Runnable {

		@Override
		public void run() {
			int pedido = 1;
			while (abierto) {
				int intervalo = ThreadLocalRandom.current().nextInt(1, 8) * 100;
				
				try {
					Thread.sleep(intervalo);
				} catch (InterruptedException e) {
				}
				
				pedidos.add(pedido);
				System.out.println(pedido);
				pedido++;
			}
		}
	}
}
