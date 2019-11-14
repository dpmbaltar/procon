package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encargada de llevar y traer los bolsos con pertenencias de los visitantes.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Camioneta implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Camioneta(Parque parque) {
        this.parque = parque;
    }

    /**
     * Lleva y trae bolsos con las pertenencias de los visitantes.
     */
    @Override
    public void run() {
        CarreraGomones carrera = parque.getCarreraGomones();

        try {
            while (parque.estaAbierto() || carrera.hayVisitantesEnInicio()) {
                carrera.llevarBolsosAlFinal();
//                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
                carrera.traerBolsosAlInicio();
//                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
