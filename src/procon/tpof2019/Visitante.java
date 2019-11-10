package procon.tpof2019;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Visitante implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Visitante(Parque parque) {
        this.parque = parque;
    }

    /**
     * Visita el parque.
     */
    @Override
    public void run() {
        while (parque.estaAbierto()) {
            try {
                sleep(1, 30);
                parque.irACarrerasDeGomones();
                sleep(1, 2);
                parque.iniciarCarreraDeGomones();
            } catch (InterruptedException e) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void sleep(int min, int max) throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(min, max) * 1000);
    }

}
