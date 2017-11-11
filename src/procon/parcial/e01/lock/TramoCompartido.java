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
     * Indica si la vía esta ocupada.
     */
    private boolean ocupado = false;

    /**
     * El tren que está pasando por la vía compartida.
     */
    private Tren tren;
    private String nombre;
    private char tramo;

    /**
     * Cantidad en espera en el tramo A.
     */
    private int cantidadA = 0;

    /**
     * Cantidad en espera en el tramo O.
     */
    private int cantidadB = 0;

    /**
     * Cola de trenes en espera desde el tramo A.
     */
    private final ArrayDeque<Tren> tramoA = new ArrayDeque<>();

    /**
     * Cola de trenes en espera desde el tramo B.
     */
    private final ArrayDeque<Tren> tramoB = new ArrayDeque<>();

    /**
     * Lock.
     */
    private final ReentrantLock cierre = new ReentrantLock(true);

    /**
     * Condición para indicar que un tren está esperando en el tramo A.
     */
    private final Condition esperarA = cierre.newCondition();

    /**
     * Condición para indicar que un tren está esperando en el tramo B.
     */
    private final Condition esperarB = cierre.newCondition();

    /**
     * Simula entrar un tren al tramo compartido.
     * @param nombre
     * @param tramo
     * @throws InterruptedException
     */
    public void entrar(String nombre, char tramo) throws InterruptedException {
        cierre.lock();
        try {
            while (ocupado) {
                if (tramo == 'A') {
                    System.out.println(nombre+" espera en A...");
                    cantidadA++;
                    esperarA.await();
                } else if (tramo == 'B') {
                    System.out.println(nombre+" espera en B...");
                    cantidadB++;
                    esperarB.await();
                }
            }

            System.out.println(nombre+" entra al tramo compartido desde "+tramo);
            ocupar(nombre, tramo);
        } finally {
            cierre.unlock();
        }
    }

    /**
     * Simula salir un tren del tramo compartido.
     */
    public void salir() {
        cierre.lock();
        try {
            if (tramo == 'A') {
                if (cantidadB > 0) {
                    cantidadB--;
                    esperarB.signal();
                } else if (cantidadA > 0) {
                    cantidadA--;
                    esperarA.signal();
                }
            } else if (tramo == 'B') {
                if (cantidadA > 0) {
                    cantidadA--;
                    esperarA.signal();
                } else if (cantidadB > 0) {
                    cantidadB--;
                    esperarB.signal();
                }
            }

            System.out.println(nombre+" sale del tramo compartido.");
            desocupar();
        } finally {
            cierre.unlock();
        }
    }

    private void ocupar(String n, char t) {
        nombre = n;
        tramo = t;
        ocupado = true;
    }

    private void desocupar() {
        nombre = "";
        tramo = ' ';
        ocupado = false;
    }
}
