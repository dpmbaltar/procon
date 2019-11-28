package procon.tpof2019;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nado con delfines.
 * Implementado con Locks.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class NadoDelfines {

    /**
     * Cantidad de piletas.
     */
    public static final int CANTIDAD_PILETAS = 4;

    /**
     * Capacidad de las piletas.
     */
    public static final int CAPACIDAD_PILETAS = 10;

    /**
     * Los horarios de la actividad.
     */
    private final int[] horarios;

    /**
     * Matríz de lugares adquiridos por horario.
     */
    private final int[][] lugares;

    /**
     * Indica inicio de actividad.
     */
    private final boolean[] inicio;

    /**
     * Indica cancelación de actividad.
     */
    private final boolean[] cancelado;

    /**
     * El último lugar que se puede adquirir.
     */
    private final int ultimoLugar;

    /**
     * El próximo lugar a ser adquirido.
     */
    private int proximoLugar;

    /**
     * El tiempo del parque.
     */
    private final Tiempo tiempo;

    /**
     * Mutex.
     */
    private final Lock mutex;

    /**
     * Condición de inicio de actividad en piletas.
     */
    private final Condition[] piletaInicia;

    /**
     * Condición de finalización de actividad en piletas.
     */
    private final Condition[] piletaFinaliza;

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Constructor con los horarios de la actividad.
     *
     * @param horarios los horarios de inicio de la actividad
     */
    public NadoDelfines(Tiempo tiempo, int horarios[]) {
        this.tiempo = tiempo;
        this.horarios = horarios;
        this.lugares = new int[horarios.length][CANTIDAD_PILETAS];
        this.inicio = new boolean[CANTIDAD_PILETAS];
        this.cancelado = new boolean[CANTIDAD_PILETAS];
        this.ultimoLugar = horarios.length * CANTIDAD_PILETAS * CAPACIDAD_PILETAS;
        this.proximoLugar = 0;
        this.mutex = new ReentrantLock();
        this.piletaInicia = new Condition[CANTIDAD_PILETAS];
        this.piletaFinaliza = new Condition[CANTIDAD_PILETAS];

        for (int i = 0; i < CANTIDAD_PILETAS; i++) {
            this.piletaInicia[i] = this.mutex.newCondition();
            this.piletaFinaliza[i] = this.mutex.newCondition();
        }
    }

    /**
     * Intenta adquirir un lugar en un horario disponible de la actividad, y devueve -1 si no hay lugar en alguno de
     * los horarios establecidos, o un entero mayor o igual a cero indicando el lugar adquirido.
     *
     * @return un entero indicando el lugar, o -1 si no pudo adquirir uno
     */
    public int adquirirLugar() {
        int lugar = -1;

        mutex.lock();
        try {
            int hora = tiempo.getHora();

            // Adquirir lugar solo si aún hay horarios y lugares disponibles
            if (hora < horarios[horarios.length - 1] && proximoLugar < ultimoLugar) {
                int horario = obtenerHorario(proximoLugar);
                int pileta = obtenerPileta(proximoLugar);
                lugares[horario][pileta]++;
                lugar = proximoLugar;
                proximoLugar++;
            }
        } finally {
            mutex.unlock();
        }

        return lugar;
    }

    /**
     * Visitante entra a la pileta e inicia la actividad.
     *
     * @param lugar el lugar adquirido
     * @return verdadero si inició la actividad, falso en caso contrario
     * @throws InterruptedException
     */
    public boolean entrarPileta(int lugar) throws InterruptedException {
        boolean entroPileta = false;

        mutex.lock();
        try {
            String visitante = Thread.currentThread().getName();
            int hora = tiempo.getHora();
            int numeroHorario = obtenerHorario(lugar);
            int numeroPileta = obtenerPileta(lugar);

            // Esperar a que inicie la actividad en la pileta asignada
            while (hora < horarios[numeroHorario] || (!inicio[numeroPileta] && !cancelado[numeroPileta])) {
                piletaInicia[numeroPileta].await();
                hora = tiempo.getHora();
            }

            int minuto = tiempo.getMinuto();

            // La actividad puede haber sido cancelada
            if (inicio[numeroPileta]) {
                entroPileta = true;
                vista.printNadoDelfines(String.format("%s inicia en pileta %d a las %02d:%02d hs", visitante,
                        numeroPileta, hora, minuto));
                vista.agregarVisitanteNadoDelfines();
                vista.agregarVisitantePileta(numeroPileta);
            } else {
                lugares[numeroHorario][numeroPileta]--;
                vista.printNadoDelfines(String.format("%s no inicia en pileta %d a las %02d:%02d hs (cancelado)",
                        visitante, numeroPileta, hora, minuto));
            }
        } finally {
            mutex.unlock();
        }

        return entroPileta;
    }

    /**
     * Visitante sale de la pileta y finaliza la actividad.
     *
     * @param lugar el lugar adquirido
     * @throws InterruptedException
     */
    public void salirPileta(int lugar) throws InterruptedException {
        mutex.lock();
        try {
            String visitante = Thread.currentThread().getName();
            int horario = obtenerHorario(lugar);
            int pileta = obtenerPileta(lugar);

            // Esperar a que finalice la actividad en la pileta asignada
            while (inicio[pileta])
                piletaFinaliza[pileta].await();

            lugares[horario][pileta]--;
            vista.printNadoDelfines(String.format("%s finaliza en pileta %d", visitante, pileta));
            vista.sacarVisitanteNadoDelfines();
            vista.sacarVisitantePileta(pileta);
        } finally {
            mutex.unlock();
        }
    }

    /**
     * Administrador de piletas inicia la actividad en el horario dado.
     *
     * @param horario el número de horario
     * @throws InterruptedException
     */
    public void iniciar(int horario) throws InterruptedException {
        mutex.lock();
        try {
            String administrador = Thread.currentThread().getName();
            int hora = tiempo.getHora();
            int piletasCompletas = 0;

            // Esperar a que sea el horario
            while (hora < horarios[horario]) {
                mutex.unlock();
                tiempo.dormir(horarios[horario]); // campos tiempo y horarios son finales, y horario un argumento
                mutex.lock();
                hora = tiempo.getHora();
            }

            // Verificar piletas completas
            for (int i = 0; i < lugares[horario].length; i++) {
                if (lugares[horario][i] == 10) {
                    piletasCompletas++;
                }
            }

            int minuto = tiempo.getMinuto();

            // Iniciar actividad si de las n piletas, al menos n - 1 piletas estén completas
            if (piletasCompletas >= (lugares[horario].length - 1)) {
                for (int i = 0; i < piletaInicia.length; i++) {
                    inicio[i] = true;
                    piletaInicia[i].signalAll();
                }

                vista.printNadoDelfines(String.format("%s inicia a las %02d:%02d hs", administrador, hora, minuto));
            } else {
                for (int i = 0; i < piletaInicia.length; i++) {
                    cancelado[i] = true;
                    piletaInicia[i].signalAll();
                }

                vista.printNadoDelfines(String.format("%s cancela horario %02d:%02d hs", administrador, hora, minuto));
            }
        } finally {
            mutex.unlock();
        }

        Thread.sleep(Tiempo.enMinutos(45));
    }

    /**
     * Administrador de piletas finaliza la actividad en el horario dado.
     *
     * @param horario el número de horario
     */
    public void finalizar(int horario) {
        mutex.lock();
        try {
            String administrador = Thread.currentThread().getName();
            int hora = tiempo.getHora();
            int minuto = tiempo.getMinuto();

            for (int i = 0; i < piletaFinaliza.length; i++) {
                inicio[i] = false;
                cancelado[i] = false;
                piletaFinaliza[i].signalAll();
            }

            vista.printNadoDelfines(String.format("%s finaliza a las %02d:%02d hs", administrador, hora, minuto));
        } finally {
            mutex.unlock();
        }
    }

    /**
     * Devuelve todos los horarios de la actividad.
     *
     * @return los horarios de la actividad
     */
    public int[] getHorarios() {
        return horarios;
    }

    /**
     * Obtiene el número de horario a partir de un lugar.
     *
     * @param lugar el lugar asignado
     * @return el número de horario
     */
    private int obtenerHorario(int lugar) {
        return lugar / (CAPACIDAD_PILETAS * CANTIDAD_PILETAS);
    }

    /**
     * Obtiene el número de pileta a partir de un lugar.
     *
     * @param lugar el lugar asignado
     * @return el número de la pileta
     */
    private int obtenerPileta(int lugar) {
        return (lugar / CAPACIDAD_PILETAS) % CANTIDAD_PILETAS;
    }

}
