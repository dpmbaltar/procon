package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Tour que realizan los visitantes para visitar el {@link Parque}.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Tour {

    /**
     * Cantidad máxima de visitantes por tour.
     */
    public static final int CAPACIDAD_TOUR = 25;

    /**
     * Utilizada para iniciar el tour hacia el parque.
     */
    private final CyclicBarrier iniciarTour = new CyclicBarrier(CAPACIDAD_TOUR);

    /**
     * Utilizada para ingresar al parque.
     */
    private final CyclicBarrier ingresarAlParque = new CyclicBarrier(CAPACIDAD_TOUR);

    /**
     * Utilizada para finalizar el tour en el parque.
     */
    private final CyclicBarrier finalizarTour = new CyclicBarrier(CAPACIDAD_TOUR);

    /**
     * Cantidad de visitantes en el tour.
     */
    private int cantidadVisitantes = 0;

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Iniciar tour (ir al parque).
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public boolean iniciar() throws InterruptedException, BrokenBarrierException {
        boolean iniciaTour = false;

        synchronized (this) {
            if (cantidadVisitantes < CAPACIDAD_TOUR) {
                cantidadVisitantes++;
                iniciaTour = true;
                vista.printParque(String.format("%s inicia tour", Thread.currentThread().getName()));
                vista.agregarVisitanteTour();
            }
        }

        if (iniciaTour) {
            iniciarTour.await();
            Thread.sleep(Tiempo.enMinutos(45));
            ingresarAlParque.await();
            Thread.sleep(Tiempo.enMinutos(5));
        }

        return iniciaTour;
    }

    /**
     * Finalizar tour (volver del parque).
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void finalizar() throws InterruptedException, BrokenBarrierException {
        finalizarTour.await();

        synchronized (this) {
            //cantidadVisitantes--;
            vista.printParque(String.format("%s finaliza tour", Thread.currentThread().getName()));
            vista.sacarVisitanteTour();
        }
    }

}
