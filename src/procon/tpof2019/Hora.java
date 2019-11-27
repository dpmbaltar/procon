package procon.tpof2019;

/**
 * La hora del parque (actualizada por el {@link Reloj} del parque).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Hora {

    /**
     * La hora (entre 0 y 23).
     */
    private int hora;

    /**
     * El minuto (entre 0 y 59).
     */
    private int minuto;

    /**
     * Constructor vacío, con hora cero y minuto cero.
     */
    public Hora() {
        this(0, 0);
    }

    /**
     * Constructor con la hora y el minuto.
     *
     * @param hora la hora
     * @param minuto el minuto
     */
    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    /**
     * Devuelve la hora.
     *
     * @return la hora
     */
    public synchronized int getHora() {
        return hora;
    }
    /**
     * Establece la hora.
     *
     * @param hora la hora
     */
    public synchronized void setHora(int hora) {
        this.hora = hora;
    }
    /**
     * Devuelve el minuto.
     *
     * @return el minuto
     */
    public synchronized int getMinuto() {
        return minuto;
    }
    /**
     * Establece el minuto.
     *
     * @param minuto el minuto
     */
    public synchronized void setMinuto(int minuto) {
        this.minuto = minuto;
    }

}
