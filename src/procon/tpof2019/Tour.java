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
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Iniciar tour (ir al parque).
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void iniciar() throws InterruptedException, BrokenBarrierException {
        vista.printParque(String.format("%s inicia tour", Thread.currentThread().getName()));
        vista.agregarVisitanteTour();
        iniciarTour.await();

        Thread.sleep(Tiempo.enHoras(1));

        ingresarAlParque.await();
    }

    /**
     * Finalizar tour (volver del parque).
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void finalizar() throws InterruptedException, BrokenBarrierException {
        finalizarTour.await();
        vista.printParque(String.format("%s finaliza tour", Thread.currentThread().getName()));
        vista.sacarVisitanteTour();
    }

}
