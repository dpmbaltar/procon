package procon.tpof2019;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nado con delfines.
 * Implementado con
 */
public class NadoDelfines {

    public static final int CAPACIDAD_PILETAS = 10;
    public static final int CANTIDAD_PILETAS = 4;
    public static final int CANTIDAD_HORARIOS = 4;

    /**
     * Matríz con los horarios y lugares adquiridos.
     */
    private final int lugares[][];
    private final boolean inicio[];
    private final Lock mutex;
    private final Condition[] piletasListas;
    private int ultimoLugar = 0;

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    public NadoDelfines() {
        lugares = new int[CANTIDAD_HORARIOS][CANTIDAD_PILETAS];
        inicio = new boolean[CANTIDAD_PILETAS];
        mutex = new ReentrantLock();
        piletasListas = new Condition[CANTIDAD_PILETAS];

        for (int i = 0; i < piletasListas.length; i++)
            piletasListas[i] = mutex.newCondition();
    }

    public int adquirirLugar() {
        int lugarAsignado = -1;
        // TODO Auto-generated method stub
        return lugarAsignado;
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
            int horario = (lugar / CAPACIDAD_PILETAS) / CANTIDAD_HORARIOS;
            int pileta = (lugar / CAPACIDAD_PILETAS) % CANTIDAD_PILETAS;

            // Esperar hasta que la actividad en la pileta asignada inicie
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
