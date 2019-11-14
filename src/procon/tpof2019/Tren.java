package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tren implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Tren(Parque parque) {
        this.parque = parque;
    }

    @Override
    public void run() {
        try {
            while (parque.estaAbierto()) {
                parque.llevarBolsosAlFinal();
//                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
                parque.traerBolsosAlInicio();
//                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
