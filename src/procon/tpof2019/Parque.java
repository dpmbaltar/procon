package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Parque ecológico, clase principal desde donde se accede a las actividades.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Parque {

    /**
     * Hora de abertura el parque.
     */
    public static final int HORA_ABRE = 9;

    /**
     * Hora de cierre del parque.
     */
    public static final int HORA_CIERRA = 18;

    /**
     * Hora de cierre de actividades del parque.
     */
    public static final int HORA_CIERRA_ACTIVIDADES = 17;

    /**
     * Cantidad máxima de visitantes por tour.
     */
    private static final int CAPACIDAD_TOUR = 25;

    /**
     * Cantidad de molinetes de la entrada al parque.
     */
    private static final int CANTIDAD_MOLINETES = 5;

    /**
     * Indica si el parque está abierto o cerrado.
     */
    private boolean abierto = false;

    /**
     * Indica si las actividades del parque están abiertas o cerradas.
     */
    private boolean actividadesAbiertas = false;

    /**
     * El shop del parque.
     */
    private final Shop shop = new Shop();

    /**
     * Los restaurantes del parque.
     */
    private final Restaurante[] restaurantes;

    /**
     * La actividad "Carrera de gomones por el río".
     */
    private final CarreraGomones carreraGomones;

    /**
     * La actividad "Faro-Mirador con vista a 40 m de altura y descenso en tobogán".
     */
    private final FaroMirador faroMirador;

    /**
     * Molinetes de la entrada al parque (5 en total, se liberan al abrir el parque).
     */
    private final Semaphore molinetes = new Semaphore(0, true);

    /**
     * Utilizada para iniciar el tour hacia el parque.
     */
    private final CyclicBarrier iniciarTour = new CyclicBarrier(CAPACIDAD_TOUR);

    /**
     * Utilizada para finalizar el tour en el parque.
     */
    private final CyclicBarrier finalizarTour = new CyclicBarrier(CAPACIDAD_TOUR);

    /**
     * Indica la cantidad de visitantes listos para iniciar el tour.
     */
    private int visitantesEnTour = 0;

    /**
     * Cantidad de visitantes que está en el parque.
     */
    private int visitantes = 0;

    private final VistaParque vista = VistaParque.getInstance();

    /**
     * Constructor.
     */
    public Parque() {
        int capacidadRestaurantes = 10;
        restaurantes = new Restaurante[3];

        for (int i = 0; i < restaurantes.length; i++) {
            restaurantes[i] = new Restaurante(i, capacidadRestaurantes);
            capacidadRestaurantes += 5;
        }

        carreraGomones = new CarreraGomones(this);
        faroMirador = new FaroMirador();
    }

    /**
     * Devuelve el shop.
     *
     * @return el shop
     */
    public synchronized Shop getShop() {
        return shop;
    }

    /**
     * Devuelve el restaurante n.
     *
     * @param n el número del restaurante
     * @return el restaurante
     */
    public synchronized Restaurante getRestaurante(int n) {
        return restaurantes[n];
    }

    /**
     * Devuelve la instancia de CarreraGomones.
     *
     * @return instancia de CarreraGomones
     */
    public synchronized CarreraGomones getCarreraGomones() {
        return carreraGomones;
    }

    /**
     * Devuelve la instancia de FaroMirador.
     *
     * @return instancia de FaroMirador
     */
    public synchronized FaroMirador getFaroMirador() {
        return faroMirador;
    }

    /**
     * Devuelve la cantidad de visitantes en el parque.
     *
     * @return la cantidad de visitantes
     */
    public synchronized int getVisitantes() {
        return visitantes;
    }

    /**
     * Abre el parque.
     */
    public synchronized void abrir() {
        abierto = true;
        actividadesAbiertas = true;
        molinetes.release(CANTIDAD_MOLINETES);
        vista.printParque("<<PARQUE ABIERTO>>");
    }

    /**
     * Cierra las actividades del parque.
     */
    public synchronized void cerrarActividades() {
        actividadesAbiertas = false;
        vista.printParque("<<ACTIVIDADES CERRADAS>>");
    }

    /**
     * Cierra el parque.
     */
    public synchronized void cerrar() {
        abierto = false;
        vista.printParque("<<PARQUE CERRADO>>");
    }

    /**
     * Devuelve verdadero si el parque está abierto.
     *
     * @return verdadero si está abierto, falso en caso contrario
     */
    public synchronized boolean estaAbierto() {
        return abierto;
    }

    /**
     * Devuelve verdadero si las actividades están abiertas.
     *
     * @return verdadero si están abiertas, falso en caso contrario
     */
    public synchronized boolean actividadesAbiertas() {
        return actividadesAbiertas;
    }

    /**
     * Inicia la visita hacia el parque.
     *
     * @return verdadero si va en tour, falso en caso contrario
     * @throws InterruptedException
     */
    public synchronized boolean iniciarVisita() throws InterruptedException {
        boolean enTour = false;

        if (visitantesEnTour < CAPACIDAD_TOUR) {
            visitantesEnTour++;
            enTour = true;
        }

        return enTour;
    }

    /**
     * Ir al parque en particular.
     */
    public void irParticular() {
        vista.printParque(String.format("%s viaja al parque en particular", Thread.currentThread().getName()));
    }

    /**
     * Iniciar tour (ir al parque).
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void iniciarTour() throws InterruptedException, BrokenBarrierException {
        vista.printTour(String.format("%s inicia viaje al parque en tour", Thread.currentThread().getName()));
        iniciarTour.await();
        Thread.sleep(1000);
    }

    /**
     * Finalizar tour (llegar al parque).
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void finalizarTour() throws InterruptedException, BrokenBarrierException {
        finalizarTour.await();
        vista.printTour(String.format("%s finaliza viaje al parque en tour", Thread.currentThread().getName()));
    }

    /**
     * Entra al parque a través de los molinetes.
     *
     * @throws InterruptedException
     */
    public void entrar() throws InterruptedException {
        String visitante = Thread.currentThread().getName();
        vista.printParque(String.format("%s llega a los molinetes", visitante));
        molinetes.acquire();
        Thread.sleep(ThreadLocalRandom.current().nextInt(0, 3) * 100);
        vista.printParque(String.format("%s entra al parque", visitante));
        molinetes.release();

        synchronized (this) {
            visitantes++;
        }
    }

    /**
     * Finaliza visita en el parque.
     */
    public synchronized void finalizarVisita() {
        vista.printParque(String.format("%s finaliza visita al parque", Thread.currentThread().getName()));
        visitantes--;
    }

}
