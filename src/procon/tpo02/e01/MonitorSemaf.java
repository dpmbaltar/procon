/**
 * Trabajo Práctico Obligatorio 02
 * Ejercicio 01
 */
package procon.tpo02.e01;

/**
 * Monitor que simula un semáforo general.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class MonitorSemaf {

    /**
     * Cantidad de permisos.
     */
    private int permisos;

    /**
     * Constructor.
     */
    public MonitorSemaf() {
        this(0);
    }

    /**
     * Constructor con n cantidad de permisos.
     *
     * @param n cantidad de permisos
     */
    public MonitorSemaf(int n) {
        permisos = n;
    }

    /**
     * Verifica si tiene n cantidad de permisos disponibles.
     *
     * @param n cantidad de permisos
     * @return
     */
    private boolean tienePermisos(int n) {
        return permisos > 0 && n <= permisos;
    }

    /**
     * Adquiere un permiso.
     *
     * @throws InterruptedException
     */
    public synchronized void acquireM() throws InterruptedException {
        acquireM(1);
    }

    /**
     * Adquiere n permisos.
     *
     * @param n cantidad de permisos
     */
    public synchronized void acquireM(int n) throws InterruptedException {
        if (n < 0)
            throw new IllegalArgumentException(
                    "Cantidad de permisos negativos");
        while (!tienePermisos(n))
            wait();
        permisos -= n;
    }

    /**
     * Libera un permiso.
     */
    public synchronized void releaseM() {
        releaseM(1);
    }

    /**
     * Libera n permisos.
     *
     * @param n cantidad de permisos
     */
    public synchronized void releaseM(int n) {
        if (n < 0)
            throw new IllegalArgumentException(
                    "Cantidad de permisos negativos");
        permisos += n;
        notifyAll();
    }

    /**
     * Intenta adquirir un permiso, y si lo adquiere, retorna verdadero. Caso
     * contrario solo devuelve falso.
     *
     * @return
     */
    public synchronized boolean tryAdquireM() {
        boolean adquiere = false;
        if (tienePermisos(1)) {
            permisos--;
            adquiere = true;
        }

        return adquiere;
    }
}
