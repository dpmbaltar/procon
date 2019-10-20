/**
 * Parcial - Ejercicio 2
 */
package procon.parcial.e02;

import java.util.Random;

/**
 * El proceso de carga de autos.
 */
public class LadoA implements Runnable {

    /**
     * La instancia del trasbordador.
     */
    private Trasbordador trasbordador;

    /**
     * Constructor.
     * @param t
     */
    public LadoA(Trasbordador t) {
        trasbordador = t;
    }

    @Override
    public void run() {
        int auto = 0;
        while (trasbordador.estaFuncionando()) {
            auto++;
            int demora = (new Random()).nextInt(10) * 100;

            try {
                trasbordador.cargar("Auto A-" + auto);
                Thread.sleep(demora);
            } catch (InterruptedException e) {}
        }
    }
}
