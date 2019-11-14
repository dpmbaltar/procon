package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encargado de indicar en cual tobogán descenderá un visitante del Faro-Mirador.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class AdministradorTobogan implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public AdministradorTobogan(Parque parque) {
        this.parque = parque;
    }

    /**
     * Asigna uno de los toboganes a los visitantes.
     */
    @Override
    public void run() {
        FaroMirador faroMirador = parque.getFaroMirador();

        try {
            while (faroMirador.hayVisitantes()) {
                faroMirador.asignarTobogan();
            }

            System.out.println(String.format("<<%s finaliza>>", Thread.currentThread().getName()));
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
