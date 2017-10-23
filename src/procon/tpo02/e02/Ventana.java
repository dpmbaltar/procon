/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 02
 */
package procon.tpo02.e02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ventana pasa comidas.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Ventana {

	/**
	 * Lock de la ventana.
	 */
	private final Lock cerrojo = new ReentrantLock(true);
	
	/**
	 * Indica cuando un pedido esta listo para ser servido al cliente.
	 */
	private final Condition pedidoListo = cerrojo.newCondition();
	
	/**
	 * Indica cuando un pedido esta en proceso por el Chef.
	 */
	private final Condition enProceso = cerrojo.newCondition();
	
	/**
	 * Indica al Chef que hay un nuevo pedido.
	 */
	private final Condition nuevoPedido = cerrojo.newCondition();
	
	/**
	 * Numero de pedido en proceso.
	 */
	private int pedido = 0;
	
	/**
	 * Determina si el pedido está listo, esperando a ser entregado al cliente.
	 */
	private boolean listo = false;
	
	/**
	 * Constructor.
	 */
	public Ventana() {
	}
	
	/**
	 * Solicita (el mozo) un pedido al Chef.
	 * @param pedido
	 * @throws InterruptedException
	 */
	public void solicitar(int pedido) throws InterruptedException {
		cerrojo.lock();
		try {
			System.out.println("Solicitar pedido #"+pedido);
			while (this.pedido != 0) {
				System.out.println("Esperar proceso de pedido #"+this.pedido);
				enProceso.await();
			}
			this.pedido = pedido;
			System.out.println("Avisar de nuevo pedido");
			nuevoPedido.signal();
		} finally {
			cerrojo.unlock();
		}
	}
	
	/**
	 * Sirve el pedido al cliente (mozo).
	 * @throws InterruptedException
	 */
	public void servir() throws InterruptedException {
		cerrojo.lock();
		try {
			System.out.println("Servir pedido #"+this.pedido);
			while (!listo) {
				System.out.println("Esperar que el pedido este listo #"+this.pedido);
				pedidoListo.await();
			}
			listo = false;
		} finally {
			cerrojo.unlock();
		}
	}
	
	/**
	 * Chef entrega un pedido listo a la ventana para ser servido.
	 * @param pedido
	 * @throws InterruptedException
	 */
	public void entregar(int pedido) throws InterruptedException {
		cerrojo.lock();
		try {
			System.out.println("Entregar pedido #"+this.pedido);
			this.pedido = 0;
			listo = true;
			pedidoListo.signal();
			enProceso.signal();
		} finally {
			cerrojo.unlock();
		}
	}
	
	/**
	 * Chef toma pedido solicitado por el mozo.
	 * @return
	 * @throws InterruptedException
	 */
	public int tomar() throws InterruptedException {
		int nroPedido;
		cerrojo.lock();
		try {
			System.out.println("Tomar pedido");
			while (this.pedido == 0) {
				System.out.println("Esperar un nuevo pedido...");
				nuevoPedido.await();
			}
			nroPedido = pedido;
			System.out.println("Pedido tomado");
		} finally {
			cerrojo.unlock();
		}
		
		return nroPedido;
	}
}
