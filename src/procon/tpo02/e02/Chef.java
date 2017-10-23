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

	private Ventana ventana;
	
	public Chef(Ventana ventana) {
		this.ventana = ventana;
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
		int pedido = ventana.tomar();
		Thread.sleep(500);
		ventana.entregar(pedido);
	}
}
