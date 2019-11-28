package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Envargado de iniciar las carreras de gomones.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class AdministradorCarrera implements Runnable {

    /**
     * El parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public AdministradorCarrera(Parque parque) {
        this.parque = parque;
    }

    /**
     * Larga las carreras de gomones.
     */
    @Override
    public void run() {
        CarreraGomones carreraGomones = parque.getCarreraGomones();

        try {
            while (!parque.finalizado() || carreraGomones.hayVisitantesEnInicio() || parque.getVisitantes() > 0) {
                carreraGomones.largarCarrera();
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
