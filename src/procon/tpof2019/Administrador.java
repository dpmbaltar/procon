package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encargado de abrir y cerrar el parque de acorde a los horarios establecidos.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Administrador implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Administrador(Parque parque) {
        this.parque = parque;
    }

    /**
     * Abre y cierra el parque según los horarios.
     */
    @Override
    public void run() {
        try {
            parque.abrir();
            Thread.sleep(Tiempo.enHoras(Parque.HORA_CIERRA_ACTIVIDADES - Parque.HORA_ABRE));
            parque.cerrarActividades();
            Thread.sleep(Tiempo.enHoras(Parque.HORA_CIERRA - Parque.HORA_CIERRA_ACTIVIDADES));
            parque.cerrar();
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
