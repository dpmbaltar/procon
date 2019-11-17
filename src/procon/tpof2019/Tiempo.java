package procon.tpof2019;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Relación entre el tiempo real y el que transcurre en el parque.
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
