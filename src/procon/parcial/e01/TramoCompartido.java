package procon.parcial.e01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Representa el tramo compartido por tramos A y B.
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

    private int cantidadA = 0;
    private int cantidadB = 0;

    private ReentrantLock cierre = new ReentrantLock();
    private Condition esperarA = cierre.newCondition();
    private Condition esperarB = cierre.newCondition();

    private void ocupar(Tren t) {
        tren = t;
        ocupado = true;
    }

    private void desocupar() {
        tren = null;
        ocupado = false;
    }

    public void entrar(Tren tren) throws InterruptedException {
        cierre.lock();
        try {
            String nombre = tren.getNombre();
            char tramo = tren.getTramo();

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
            ocupar(tren);
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
            String nombre = tren.getNombre();
            char tramo = tren.getTramo();

            if (tramo == 'A') {
                if (cantidadB > 0) {
                    cantidadB--;
                    esperarB.signal();
                } else {
                    esperarA.signal();
                }
            } else if (tramo == 'B') {
                if (cantidadA > 0) {
                    cantidadA--;
                    esperarA.signal();
                } else {
                    esperarB.signal();
                }
            }

            System.out.println(nombre+" sale del tramo compartido.");
            desocupar();
        } finally {
            cierre.unlock();
        }
    }
}
