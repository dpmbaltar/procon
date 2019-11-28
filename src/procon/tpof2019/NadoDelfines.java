package procon.tpof2019;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nado con delfines.
 * Implementado con
 */
public class NadoDelfines {


    public static final int CANTIDAD_PILETAS = 4;
    public static final int CAPACIDAD_PILETAS = 10;

    /**
     * Los horarios de la actividad.
     */
    private final int horarios[];

    /**
     * Matríz de lugares adquiridos por horario.
     */
    private final int lugares[][];
    private final boolean inicio[];
    private final int ultimoLugar;
    private int proximoLugar;
    private final Tiempo tiempo;
    private final Lock mutex;
    private final Condition[] piletaInicia;
    private final Condition[] piletaFinaliza;
    private final Condition iniciarHorario;

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
        this.ultimoLugar = horarios.length * CANTIDAD_PILETAS * CAPACIDAD_PILETAS;
        this.proximoLugar = 0;
        this.mutex = new ReentrantLock();
        this.iniciarHorario = mutex.newCondition();
        this.piletaInicia = new Condition[CANTIDAD_PILETAS];
        this.piletaFinaliza = new Condition[CANTIDAD_PILETAS];

        for (int i = 0; i < CANTIDAD_PILETAS; i++) {
            this.piletaInicia[i] = this.mutex.newCondition();
            this.piletaFinaliza[i] = this.mutex.newCondition();
        }
    }

    private int obtenerHorario(int lugar) {
        return lugar / (CAPACIDAD_PILETAS * CANTIDAD_PILETAS);
    }

    private int obtenerPileta(int lugar) {
        return (lugar / CAPACIDAD_PILETAS) % CANTIDAD_PILETAS;
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
            if (proximoLugar < ultimoLugar) {
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
     * Visitante inicia la actividad.
     *
     * @param lugar el lugar adquirido
     * @throws InterruptedException
     */
    public void entrarPileta(int lugar) throws InterruptedException {
        mutex.lock();
        try {
            String visitante = Thread.currentThread().getName();
            int hora = tiempo.getHora();
            int minuto = tiempo.getMinuto();
            int numHorario = obtenerHorario(lugar);
            int numPileta = obtenerPileta(lugar);

            // Esperar a que inicie la actividad en la pileta asignada
            while (hora < horarios[numHorario] || !inicio[numPileta]) {
                piletaInicia[numPileta].await();
                hora = tiempo.getHora();
            }

            vista.printNadoDelfines(String.format("%s inicia en pileta %d a las %02d:%02d hs", visitante, numPileta,
                    hora, minuto));
            vista.agregarVisitanteNadoDelfines();
            vista.agregarVisitantePileta(numPileta);
        } finally {
            mutex.unlock();
        }
    }

    /**
     * Visitante finaliza la actividad.
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

    public void iniciar(int horario) throws InterruptedException {
        mutex.lock();
        try {
            String administrador = Thread.currentThread().getName();
            int hora = tiempo.getHora();
            int minuto = tiempo.getMinuto();

            // Esperar a que sea el horario
            while (hora < horarios[horario]) {
                iniciarHorario.await(Tiempo.enMinutos(15), TimeUnit.MILLISECONDS);
                hora = tiempo.getHora();
            }

            // TODO: Agregar política del parque (no iniciar si no hay al menos 3 piletas completas)
            for (int i = 0; i < piletaInicia.length; i++) {
                inicio[i] = true;
                piletaInicia[i].signalAll();
            }

            vista.printNadoDelfines(String.format("%s inicia a las %02d:%02d hs", administrador, hora, minuto));
        } finally {
            mutex.unlock();
        }

        Thread.sleep(Tiempo.enMinutos(45));
    }

    public void finalizar(int horario) {
        mutex.lock();
        try {
            String administrador = Thread.currentThread().getName();
            int hora = tiempo.getHora();
            int minuto = tiempo.getMinuto();

            for (int i = 0; i < piletaFinaliza.length; i++) {
                inicio[i] = false;
                piletaFinaliza[i].signalAll();
            }

            vista.printNadoDelfines(String.format("%s finaliza a las %02d:%02d hs", administrador, hora, minuto));
        } finally {
            mutex.unlock();
        }
    }

}
