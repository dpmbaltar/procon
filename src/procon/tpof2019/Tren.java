package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encargado de llevar y traer visitantes a la actividad "Carrera de gomones por el río".
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Tren implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Indica que el tren siga andando.
     */
    private boolean andar = true;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Tren(Parque parque) {
        this.parque = parque;
    }

    /**
     * Parar tren.
     */
    public void parar() {
        this.andar = false;
    }

    /**
     * Lleva y trae visitantes a la actividad.
     */
    @Override
    public void run() {
        CarreraGomones carrera = parque.getCarreraGomones();

        try {
            while (andar) {
                carrera.llevarVisitantes();
                carrera.esperarVisitantes();
                carrera.traerVisitantes();
            }

            VistaParque.getInstance().printParque("<<Tren finaliza>>");
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
