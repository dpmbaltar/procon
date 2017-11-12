package procon.parcial.e01.lock;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import procon.parcial.e01.Tren;

/**
 * Representa el tramo compartido por tramos A y B.
 * Implementación con Lock.
 */
public class TramoCompartido {

    /**
     * El tren que está pasando por la vía compartida.
     */
    private Tren tren;

    /**
     * El tren que le corresponde pasar tan pronto el tramo esté disponible.
     */
    private Tren trenSiguiente;

    /**
     * Cola de trenes en espera desde el tramo A.
     */
    private final ArrayDeque<Tren> tramoA = new ArrayDeque<>();

    /**
     * Cola de trenes en espera desde el tramo B.
     */
    private final ArrayDeque<Tren> tramoB = new ArrayDeque<>();

    /**
     * Cerrojo.
     */
    private final ReentrantLock cierre = new ReentrantLock();

    /**
     * Condición para indicar que un tren está esperando en el tramo A.
     */
    private final Condition esperaA = cierre.newCondition();

    /**
     * Condición para indicar que un tren está esperando en el tramo B.
     */
    private final Condition esperaB = cierre.newCondition();

    /**
     * Constructor.
     */
    public TramoCompartido() {
        tren = null;
        trenSiguiente = null;
    }

    /**
     * Simula entrar un tren al tramo compartido.
     * @param tren el tren que va a entrar
     * @throws InterruptedException
     */
    public void entrar(Tren tren) throws InterruptedException {
        cierre.lock();
        try {
            String nombre = tren.getNombre();
            char tramo = tren.getTramo();

            // Esperar si está el tramo ocupado, según el tramo de origen
            while (estaOcupado() || !esSiguiente(tren)) {
                if (tramo == 'A') {
                    System.out.println(nombre+" espera en tramo A...");
                    if (!tramoA.contains(tren)) // Evitar duplicado
                        tramoA.add(tren);
                    esperaA.await();
                } else if (tramo == 'B') {
                    System.out.println(nombre+" espera en tramo B...");
                    if (!tramoB.contains(tren)) // Evitar duplicado
                        tramoB.add(tren);
                    esperaB.await();
                }
            }

            System.out.println(nombre+" entra al tramo compartido desde "+tramo);
            this.tren = tren;
        } finally {
            cierre.unlock();
        }
    }

    /**
     * Simula salir el tren que entró al tramo compartido.
     */
    public void salir() {
        cierre.lock();
        try {
            String nombre = tren.getNombre();
            char tramo = tren.getTramo();

            // Avisar al tramo opuesto para entrar, si hay trenes en espera
            // Sino, avisar al siguiente del tramo actual
            if (tramo == 'A') {
                if (!tramoB.isEmpty()) {
                    trenSiguiente = tramoB.remove();
                    esperaB.signal();
                } else if (!tramoA.isEmpty()) {
                    trenSiguiente = tramoA.remove();
                    esperaA.signal();
                } else {
                    trenSiguiente = null;
                }
            } else if (tramo == 'B') {
                if (!tramoA.isEmpty()) {
                    trenSiguiente = tramoA.remove();
                    esperaA.signal();
                } else if (!tramoB.isEmpty()) {
                    trenSiguiente = tramoB.remove();
                    esperaB.signal();
                } else {
                    trenSiguiente = null;
                }
            }

            System.out.println(nombre+" sale del tramo compartido ("+tramo+").");
            tren = null;
        } finally {
            cierre.unlock();
        }
    }

    /**
     * Determina si el tramo está ocupado.
     */
    private boolean estaOcupado() {
        return tren != null;
    }

    /**
     * Determina si el tren dado es el siguiente a pasar.
     * @param tren el tren a verificar
     * @return
     */
    private boolean esSiguiente(Tren tren) {
        return trenSiguiente == null || trenSiguiente.equals(tren);
    }
}
