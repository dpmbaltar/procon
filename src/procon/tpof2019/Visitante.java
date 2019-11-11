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
            while (parque.actividadesAbiertas()) {
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
        int gomon = -1;
        boolean irEnTren = false;//ThreadLocalRandom.current().nextBoolean();

        try {
            parque.irACarrerasDeGomones(irEnTren);
            llaveDeBolso = parque.dejarPertenencias();
            gomon = parque.prepararseParaLaCarrera();
            parque.iniciarCarreraDeGomones();
            parque.finalizarCarreraDeGomones(llaveDeBolso, gomon);
            parque.volverDeCarrerasDeGomones(irEnTren);
        } catch (InterruptedException | BrokenBarrierException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
