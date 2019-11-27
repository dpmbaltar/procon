package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Parque ecológico, clase principal desde donde se accede a las actividades.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Parque {

    /**
     * Hora que abre el parque.
     */
    public static final int HORA_ABRE = 9;

    /**
     * Hora que cierra el parque.
     */
    public static final int HORA_CIERRA = 18;

    /**
     * Hora que el parque cierra el ingreso a las actividades.
     */
    public static final int HORA_CIERRA_ACTIVIDADES = 17;

    /**
     * Cantidad máxima de visitantes por tour.
     */
    public static final int CAPACIDAD_TOUR = 25;

    /**
     * Cantidad de molinetes de la entrada al parque.
     */
    public static final int CANTIDAD_MOLINETES = 5;

    /**
     * Indica si el parque está abierto o cerrado.
     */
    private boolean abierto = false;

    /**
     * Indica si las actividades del parque están abiertas o cerradas.
     */
    private boolean actividadesAbiertas = false;

    /**
     * Molinetes de la entrada al parque.
     */
    private int molinetes = CANTIDAD_MOLINETES;

    /**
     * Cantidad de visitantes que están en el parque.
     */
    private int visitantes = 0;

    /**
     * El tiempo del parque.
     */
    private final Tiempo tiempo = new Tiempo();

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
     * La actividad "Disfruta de Snorkel ilimitado".
     */
    private final Snorkel snorkel;

    /**
     * La actividad "Nado con delfines".
     */
    private final NadoDelfines nadoDelfines;

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
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

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

        carreraGomones = new CarreraGomones();
        faroMirador = new FaroMirador();
        snorkel = new Snorkel();
        nadoDelfines = new NadoDelfines();
    }

    /**
     * Devuelve el tiempo del parque.
     *
     * @return el tiempo
     */
    public synchronized Tiempo getTiempo() {
        return tiempo;
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
     * Devuelve la instancia de Snorkel.
     *
     * @return instancia de Snorkel
     */
    public synchronized Snorkel getSnorkel() {
        return snorkel;
    }

    /**
     * Devuelve la instancia de NadoDelfines.
     *
     * @return instancia de NadoDelfines
     */
    public synchronized NadoDelfines getNadoDelfines() {
        return nadoDelfines;
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
    @Deprecated
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
        vista.printParque(String.format("%s inicia viaje al parque en tour", Thread.currentThread().getName()));
        iniciarTour.await();

        Thread.sleep(Tiempo.enHoras(1));
    }

    /**
     * Finalizar tour (llegar al parque).
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void finalizarTour() throws InterruptedException, BrokenBarrierException {
        finalizarTour.await();
        vista.printParque(String.format("%s llega al parque en tour", Thread.currentThread().getName()));
    }

    /**
     * Entra al parque a través de los molinetes.
     *
     * @throws InterruptedException
     */
    public void entrar() throws InterruptedException {
        String visitante = Thread.currentThread().getName();

        synchronized (this) {
            vista.printParque(String.format("%s llega a los molinetes", visitante));

            while (!abierto || molinetes == 0)
                this.wait();

            molinetes--;
            vista.agregarVisitanteMolinete();
        }

        Thread.sleep(Tiempo.entreMinutos(1, 5));

        synchronized (this) {
            molinetes++;
            visitantes++;
            this.notifyAll();

            vista.printParque(String.format("%s entra al parque", visitante));
            vista.sacarVisitanteMolinete();
            vista.agregarVisitante();
        }
    }

    /**
     * Finaliza visita en el parque.
     */
    public synchronized void finalizarVisita() {
        vista.printParque(String.format("%s finaliza visita al parque", Thread.currentThread().getName()));
        vista.sacarVisitante();
        visitantes--;
    }

}
