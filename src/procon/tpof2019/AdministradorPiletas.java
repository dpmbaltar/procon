package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministradorPiletas implements Runnable {

    private final Parque parque;

    public AdministradorPiletas(Parque parque) {
        this.parque = parque;
    }

    @Override
    public void run() {
        try {
            Tiempo tiempo = parque.getTiempo();
            NadoDelfines nadoDelfines = parque.getNadoDelfines();

            //while (parque.estaAbierto() || parque.getVisitantes() > 0 || (Parque.HORA_ABRE < tiempo.getHora() && tiempo.getHora() < Parque.HORA_CIERRA)) {
            for (int i = 0; i < 2; i++) {
                nadoDelfines.iniciar(i);
                nadoDelfines.finalizar(i);
            }

            VistaParque.getInstancia().printNadoDelfines("<<Adm. Piletas finaliza>>");
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
