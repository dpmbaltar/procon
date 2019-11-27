package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reloj del parque.
 */
public class Reloj implements Runnable {

    private int horas;
    private int minutos;
    private final Parque parque;
    private final VistaParque vista = VistaParque.getInstancia();

    public Reloj(Parque parque, int hora, int minuto) {
        this.parque = parque;
        this.horas = (0 <= hora && hora <= 23) ? hora : 0;
        this.minutos = (0 <= minuto && minuto <= 59) ? minuto : 0;
    }

    @Override
    public void run() {
        try {
            Hora hora = parque.getHora();

            while (parque.estaAbierto() || parque.getVisitantes() > 0 || (hora.getHora() - Parque.HORA_ABRE) < 9) {
                Thread.sleep(Tiempo.enMinutos(1));
                minutos++;

                if (minutos == 60) {
                    minutos = 0;
                    horas++;

                    if (horas == 24) {
                        horas = 0;
                    }
                }

                // Actualiza la hora del parque
                hora.setHora(horas);
                hora.setMinuto(minutos);

                //if ((minutos % 5) == 0) {
                    vista.actualizarHora(String.format("%02d:%02d", horas, minutos));
                //}
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
