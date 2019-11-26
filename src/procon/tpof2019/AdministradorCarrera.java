package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorCarrera implements Runnable {

    private final Parque parque;

    public AdministradorCarrera(Parque parque) {
        this.parque = parque;
    }

    @Override
    public void run() {
        CarreraGomones carreraGomones = parque.getCarreraGomones();

        try {
            while (true) {
                carreraGomones.largarCarrera();
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
