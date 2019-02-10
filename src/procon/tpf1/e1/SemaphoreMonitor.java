/**
 * Trabajo Práctico Final 1
 * Ejercicio 1
 */
package procon.tpf1.e1;

/**
 * Monitor que simula un semáforo general.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class SemaphoreMonitor {

    /**
     * Cantidad de permisos del semáforo-monitor.
     */
    private int permits;

    /**
     * Constructor con cero permisos.
     */
    public SemaphoreMonitor() {
        this(0);
    }

    /**
     * Constructor con una cantidad dada de permisos.
     *
     * @param permits cantidad de permisos
     */
    public SemaphoreMonitor(int permits) {
        if (permits < 0)
            throw new IllegalArgumentException(
                    "Cantidad de permisos negativos");
        this.permits = permits;
    }

    /**
     * Adquiere un permiso.
     * 
     * @throws InterruptedException
     */
    public synchronized void acquire() throws InterruptedException {
        acquire(1);
    }

    /**
     * Adquiere una cantidad dada de permisos.
     * 
     * @param permits cantidad de permisos
     * @throws InterruptedException
     */
    public synchronized void acquire(int permits) throws InterruptedException {
        if (permits < 0)
            throw new IllegalArgumentException(
                    "Cantidad de permisos negativos");
        while (this.permits < permits)
            wait();
        this.permits -= permits;
    }

    /**
     * Libera un permiso.
     */
    public synchronized void release() {
        release(1);
    }

    /**
     * Libera una cantidad dada de permisos.
     *
     * @param permits cantidad de permisos
     */
    public synchronized void release(int permits) {
        if (permits < 0)
            throw new IllegalArgumentException(
                    "Cantidad de permisos negativos");
        this.permits += permits;
        notifyAll();
    }

    /**
     * Intenta adquirir un permiso, y si lo adquiere, retorna verdadero. Caso
     * contrario solo devuelve falso.
     *
     * @return verdadero si lo adquirió, sino falso
     */
    public synchronized boolean tryAcquire() {
        return tryAcquire(1);
    }

    /**
     * Intenta adquirir una cantidad dada de permisos, y si los adquiere,
     * retorna verdadero. Caso contrario solo devuelve falso.
     *
     * @return verdadero si los adquirió, sino falso
     */
    public synchronized boolean tryAcquire(int permits) {
        boolean isAcquired = false;
        if (permits < 0)
            throw new IllegalArgumentException(
                    "Cantidad de permisos negativos");
        if (this.permits >= permits) {
            this.permits -= permits;
            isAcquired = true;
        }

        return isAcquired;
    }

}
