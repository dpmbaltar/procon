package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Administra el inicio y final de actividad en las piletas de "Nado con delfines".
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class AdministradorPiletas implements Runnable {

    /**
     * El parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public AdministradorPiletas(Parque parque) {
        this.parque = parque;
    }

    /**
     * Administra las piletas.
     */
    @Override
    public void run() {
        try {
            NadoDelfines nadoDelfines = parque.getNadoDelfines();
            int[] horarios = nadoDelfines.getHorarios();

            for (int i = 0; i < horarios.length; i++) {
                nadoDelfines.iniciar(i);
                nadoDelfines.finalizar(i);
            }

            VistaParque.getInstancia().printNadoDelfines("<<Adm. de Piletas finaliza>>");
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
