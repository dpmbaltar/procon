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
     * Indica que la camioneta siga andando.
     */
    private boolean andar = true;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Camioneta(Parque parque) {
        this.parque = parque;
    }

    /**
     * Parar camioneta.
     */
    public void parar() {
        this.andar = false;
    }

    /**
     * Lleva y trae bolsos con las pertenencias de los visitantes.
     */
    @Override
    public void run() {
        CarreraGomones carrera = parque.getCarreraGomones();

        try {
            while (andar) {
                carrera.llevarBolsosAlFinal();
                carrera.traerBolsosAlInicio();
            }

            VistaParque.getInstance().printParque("<<Camioneta finaliza>>");
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
