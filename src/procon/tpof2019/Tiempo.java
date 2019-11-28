package procon.tpof2019;

import java.util.concurrent.ThreadLocalRandom;

/**
 * El tiempo del parque (actualizado por el {@link Reloj} del parque).
 * Estáticamente, establece la relación entre el tiempo real y el que transcurre en el parque (en milisegundos).
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public final class Tiempo {

    /**
     * Duración de una hora por defecto.
     */
    public static final int DURACION_DE_HORA = 1000;

    /**
     * Duración de hora (en milisegundos).
     */
    private static int duracion = DURACION_DE_HORA;

    /**
     * La hora (entre 0 y 23).
     */
    private int hora;

    /**
     * El minuto (entre 0 y 59).
     */
    private int minuto;

    /**
     * Cantidad de hilos "durmiendo" hasta cierta hora.
     */
    private int[] durmiendo = new int[24];

    /**
     * Constructor vacío, con hora cero y minuto cero.
     */
    public Tiempo() {
        this(0, 0);
    }

    /**
     * Constructor con la hora y el minuto.
     *
     * @param hora la hora
     * @param minuto el minuto
     */
    public Tiempo(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    /**
     * "Dormirse" hasta que sea la hora especificada.
     *
     * @param hora la hora hasta donde dormir
     * @throws InterruptedException
     */
    public synchronized void dormir(int hora) throws InterruptedException {
        durmiendo[hora]++;

        while (this.hora != hora)
            wait();

        durmiendo[hora]--;
    }

    /**
     * Despierta a los que "duermen" hasta la hora especificada.
     *
     * @param hora la hora
     */
    public synchronized void despertar(int hora) {
        if (durmiendo[hora] > 0)
            notifyAll();
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

    /**
     * Establece duración de hora.
     *
     * @param duracion la duración en milisegundos
     */
    public synchronized static void setDuracion(int duracion) {
        Tiempo.duracion = duracion;
    }

    /**
     * Devuelve la duración de hora.
     *
     * @return la duración en milisegundos
     */
    public synchronized static int getDuracion() {
        return duracion;
    }

    /**
     * Convierte y devuelve la cantidad de horas dadas a milisegundos, según la duración establecida.
     *
     * @param cantidad la cantidad de horas a convertir
     * @return la cantidad de horas convertidas a milisegundos
     */
    public synchronized static int enHoras(int cantidad) {
        return duracion * cantidad;
    }

    /**
     * Convierte y devuelve la cantidad de minutos dados a milisegundos, según la duración establecida.
     *
     * @param cantidad la cantidad de minutos a convertir
     * @return la cantidad de minutos convertidos a milisegundos
     */
    public synchronized static int enMinutos(int cantidad) {
        return duracion * cantidad / 60;
    }

    /**
     * Convierte y devuelve una cantidad aleatoria de horas entre mínimo y máximo dados a milisegundos, según la
     * duración establecida.
     *
     * @param minimo el mínimo de horas (inclusive)
     * @param maximo el máximo de horas (inclusive)
     * @return la cantidad aleatoria de horas convertidas a milisegundos
     */
    public synchronized static int entreHoras(int minimo, int maximo) {
        return minimo < maximo
                ? ThreadLocalRandom.current().nextInt(Tiempo.enHoras(minimo), Tiempo.enHoras(maximo)) + 1
                : 0;
    }

    /**
     * Convierte y devuelve una cantidad aleatoria de minutos entre mínimo y máximo dados a milisegundos, según la
     * duración establecida.
     *
     * @param minimo el mínimo de de minutos (inclusive)
     * @param maximo el máximo de de minutos (inclusive)
     * @return la cantidad aleatoria de minutos convertidos a milisegundos
     */
    public synchronized static int entreMinutos(int minimo, int maximo) {
        return minimo < maximo
                ? ThreadLocalRandom.current().nextInt(Tiempo.enMinutos(minimo), Tiempo.enMinutos(maximo)) + 1
                : 0;
    }

}
