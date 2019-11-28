package procon.tpof2019;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reloj del parque (actualiza el {@link Tiempo} del parque).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Reloj implements Runnable {

    /**
     * La hora actual.
     */
    private int hora;

    /**
     * El minuto actual.
     */
    private int minuto;

    /**
     * Parque.
     */
    private final Parque parque;

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Constructor con el parque, la hora y minuto iniciales.
     *
     * @param parque el parque
     * @param hora la hora inicial
     * @param minuto el minuto inicial
     */
    public Reloj(Parque parque, int hora, int minuto) {
        this.parque = parque;
        this.hora = (0 <= hora && hora <= 23) ? hora : 0;
        this.minuto = (0 <= minuto && minuto <= 59) ? minuto : 0;
    }

    /**
     * Actualiza la hora cada un "minuto" (según la duración de hora establecida en el {@link Tiempo}).
     */
    @Override
    public void run() {
        try {
            Tiempo tiempo = parque.getTiempo();

            while (parque.estaAbierto() || parque.getVisitantes() > 0 || (tiempo.getHora() - Parque.HORA_ABRE) < 9) {
                Thread.sleep(Tiempo.enMinutos(1));
                minuto++;

                if (minuto == 60) {
                    minuto = 0;
                    hora++;

                    if (hora == 24) {
                        hora = 0;
                    }
                }

                // Actualiza la hora del parque
                tiempo.setHora(hora);
                tiempo.setMinuto(minuto);

                // Despertar cada 5 minutos
                if ((minuto % 5) == 0) {
                    tiempo.despertar(hora);
                    vista.actualizarHora(String.format("%02d:%02d", hora, minuto));
                }
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
