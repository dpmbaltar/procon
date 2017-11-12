package procon.parcial.e01;

import java.util.concurrent.Semaphore;

/**
 * Representa el tramo compartido por tramos A y B.
 * Implementación con Semaphore.
 */
public class TramoCompartidoConSemaforo implements TramoCompartido {

    /**
     * El tren que está pasando por la vía compartida.
     */
    private Tren tren;

    /**
     * Semáforo para la exclusión mutua.
     */
    private final Semaphore mutex = new Semaphore(1, true);

    /**
     * Semáforo para adquirir el tramo compartido cuando no hay otros trenes
     * esperando, ya sea en el tramo A o B.
     */
    private final Semaphore entraLibre = new Semaphore(1);

    /**
     * Semáforo para controlar los trenes que vienen desde el tramo A.
     * Se respeta el orden con "fairness".
     */
    private final Semaphore siguienteA = new Semaphore(0, true);

    /**
     * Semáforo para controlar los trenes que vienen desde el tramo B.
     * Se respeta el orden con "fairness".
     */
    private final Semaphore siguienteB = new Semaphore(0, true);

    /**
     * Constructor.
     */
    public TramoCompartidoConSemaforo() {
        tren = null;
    }

    /**
     * Simula entrar un tren al tramo compartido.
     * @param tren el tren que va a entrar
     * @throws InterruptedException
     */
    @Override
    public void entrar(Tren tren) throws InterruptedException {
        String nombre = tren.getNombre();
        char tramo = tren.getTramo();

        // Si un tren entra y no hay otros esperando en los tramos A y B,
        // entonces sigue sin esperar
        if (!entraLibre.tryAcquire()) {
            if (tramo == 'A') {
                if (!siguienteA.tryAcquire()) {
                    System.out.println(nombre+" espera en tramo A...");
                    siguienteA.acquire();
                }
            } else if (tramo == 'B') {
                if (!siguienteB.tryAcquire()) {
                    System.out.println(nombre+" espera en tramo B...");
                    siguienteB.acquire();
                }
            }
        }

        mutex.acquire();
        System.out.println(nombre+" entra al tramo compartido desde "+tramo);
        this.tren = tren;
        //mutex.release();
    }

    /**
     * Simula salir el tren que entró al tramo compartido.
     */
    @Override
    public void salir() {
        //try {
            //mutex.acquire();
            String nombre = tren.getNombre();
            char tramo = tren.getTramo();

            System.out.println(nombre + " sale del tramo compartido (" + tramo + ").");
            tren = null;

            if (tramo == 'A') {
                if (siguienteB.hasQueuedThreads()) {
                    siguienteB.release();
                } else if (siguienteA.hasQueuedThreads()) {
                    siguienteA.release();
                } else {
                    entraLibre.release();
                }
            } else if (tramo == 'B') {
                if (siguienteA.hasQueuedThreads()) {
                    siguienteA.release();
                } else if (siguienteB.hasQueuedThreads()) {
                    siguienteB.release();
                } else {
                    entraLibre.release();
                }
            }

            mutex.release();
        //} catch (InterruptedException e) {}
    }
}
