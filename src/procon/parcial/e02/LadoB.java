/**
 * Parcial - Ejercicio 2
 */
package procon.parcial.e02;

import java.util.Random;

/**
 * El proceso de descarga de autos (del otro lado del río).
 */
public class LadoB implements Runnable {

    /**
     * La instancia del trasbordador.
     */
    private Trasbordador trasbordador;

    /**
     * Constructor.
     * @param t
     */
    public LadoB(Trasbordador t) {
        trasbordador = t;
    }

    @Override
    public void run() {
        while (trasbordador.estaFuncionando()) {
            int demora = (new Random()).nextInt(10) * 100;
            try {
                trasbordador.descargar();
                Thread.sleep(demora);
            } catch (InterruptedException e) {}
        }
    }

}
