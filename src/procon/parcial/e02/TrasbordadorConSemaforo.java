/**
 * Parcial - Ejercicio 2
 */
package procon.parcial.e02;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

/**
 * El trasbordador que lleva autos de un lado del río al otro.
 */
public class TrasbordadorConSemaforo implements Trasbordador {

    /**
     * Indica si el trasbordador está en funcionamiento.
     */
    private boolean funcionando = false;

    /**
     * Contenedor de autos.
     */
    private ArrayDeque<String> autos;

    /**
     * Semáforo para controlar el acceso al contenedor de autos.
     */
    private final Semaphore mutex = new Semaphore(1);

    /**
     * Semáforo para controlar cuando ir al otro lado.
     */
    private final Semaphore ir = new Semaphore(0);

    /**
     * Semáforo para controlar cuando volver del otro lado.
     */
    private final Semaphore volver = new Semaphore(0);

    /**
     * Semáforo para controlar la carga de autos.
     */
    private final Semaphore carga = new Semaphore(CAPACIDAD);

    /**
     * Semáforo para controlar la descarga de autos.
     */
    private final Semaphore descarga = new Semaphore(0);

    /**
     * Constructor.
     */
    public TrasbordadorConSemaforo() {
        autos = new ArrayDeque<>(CAPACIDAD);
    }

    /**
     * Lleva autos al otro lado del río.
     * @throws InterruptedException
     */
    @Override
    public void ir() throws InterruptedException {
        ir.acquire();
        System.out.println("Ir al otro lado...");
        descarga.release(CAPACIDAD);
    }

    /**
     * Vuelve vacío del otro lado del río.
     * @throws InterruptedException
     */
    @Override
    public void volver() throws InterruptedException {
        volver.acquire();
        System.out.println("Volver del otro lado...");
        carga.release(CAPACIDAD);
    }

    /**
     * Carga el trasbordador con un auto.
     * @param auto
     * @throws InterruptedException
     */
    @Override
    public void cargar(String auto) throws InterruptedException {
        carga.acquire();
        mutex.acquire();
        autos.add(auto);
        System.out.print(String.format("Carga %s", auto));
        visualizarEstado();

        if (autos.size() == CAPACIDAD)
            ir.release();
        mutex.release();
    }

    /**
     * Descarga un auto del trasbordador.
     * @param auto
     * @throws InterruptedException
     */
    @Override
    public void descargar() throws InterruptedException {
        descarga.acquire();
        mutex.acquire();
        String auto = autos.remove();
        System.out.print(String.format("Descarga %s", auto));
        visualizarEstado();

        if (autos.size() == 0)
            volver.release();
        mutex.release();
    }

    @Override
    public boolean estaFuncionando() {
        return funcionando;
    }

    @Override
    public void iniciar() {
        funcionando = true;
    }

    @Override
    public void terminar() {
        funcionando = false;
    }

    private void visualizarEstado() {
        StringBuilder estado = new StringBuilder("[");
        for (int i = 0; i < CAPACIDAD; i++) {
            if (i < autos.size())
                estado.append("=");
            else
                estado.append(" ");
        }
        estado.append("]")
                .append(" ")
                .append(autos.size())
                .append("/")
                .append(CAPACIDAD);
        System.out.println(estado);
    }
}
