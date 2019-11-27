package procon.tpof2019;

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
    private final Lock mutex;
    private final Condition[] piletasListas;

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Constructor con los horarios de la actividad.
     *
     * @param horarios los horarios de inicio de la actividad
     */
    public NadoDelfines(int horarios[]) {
        this.horarios = horarios;
        this.lugares = new int[horarios.length][CANTIDAD_PILETAS];
        this.inicio = new boolean[CANTIDAD_PILETAS];
        this.ultimoLugar = horarios.length * CANTIDAD_PILETAS * CAPACIDAD_PILETAS;
        this.proximoLugar = 0;
        this.mutex = new ReentrantLock();
        this.piletasListas = new Condition[CANTIDAD_PILETAS];

        for (int i = 0; i < this.piletasListas.length; i++)
            this.piletasListas[i] = this.mutex.newCondition();
    }

    /**
     * Intenta adquirir un lugar en un horario disponible de la actividad, y devueve -1 si no hay lugar en alguno de
     * los horarios establecidos, o un entero mayor o igual a cero indicando el lugar adquirido.
     *
     * @return un entero indicando el lugar, o -1 si no pudo adquirir uno
     */
    public int adquirirLugar() {
        int lugarAdquirido = -1;

        mutex.lock();
        try {
            if (proximoLugar < ultimoLugar) {
                lugarAdquirido = proximoLugar;
                proximoLugar++;
            }
        } finally {
            mutex.unlock();
        }

        return lugarAdquirido;
    }

    /**
     * Visitante inicia la actividad.
     *
     * @param lugar el lugar adquirido
     * @throws InterruptedException
     */
    public void iniciar(int lugar) throws InterruptedException {
        mutex.lock();
        try {
            String visitante = Thread.currentThread().getName();
            int horario = (lugar / (CAPACIDAD_PILETAS * CANTIDAD_PILETAS));
            int pileta = (lugar / CAPACIDAD_PILETAS) % CANTIDAD_PILETAS;

            // Esperar a que inicie la actividad en la pileta asignada
            while (!inicio[pileta])
                piletasListas[pileta].await();

            vista.printNadoDelfines(String.format("%s inicia en pileta %d a las %d hs", visitante, pileta, horario));
            vista.agregarVisitanteNadoDelfines();
            vista.agregarVisitantePileta(pileta);
        } finally {
            mutex.unlock();
        }
    }

    public void finalizar() {
        // TODO Auto-generated method stub

    }

}
