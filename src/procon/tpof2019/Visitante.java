package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
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
        try {
            while (parque.estaAbierto()) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
                irACarreraDeGomones();
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void irAlShop() {
        //TODO
    }

    private void irACarreraDeGomones() {
        int llaveDeBolso = -1;
        boolean irEnTren = true;//ThreadLocalRandom.current().nextBoolean();

        try {
            parque.irACarrerasDeGomones(irEnTren);
//            Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
            llaveDeBolso = parque.dejarPertenencias();
            parque.iniciarCarreraDeGomones();
//            Thread.sleep(ThreadLocalRandom.current().nextInt(15, 20) * 100);
            parque.finalizarCarreraDeGomones();
            parque.tomarPertenencias(llaveDeBolso);
            parque.volverDeCarrerasDeGomones(irEnTren);
        } catch (InterruptedException | BrokenBarrierException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
