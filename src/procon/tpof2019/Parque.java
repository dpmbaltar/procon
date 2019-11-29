package procon.tpof2019;

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
     * Cantidad de molinetes de la entrada al parque.
     */
    public static final int CANTIDAD_MOLINETES = 5;

    /**
     * Indica que el parque ya abrió y cerro en el día.
     */
    private boolean finalizado = false;

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
    private final Tiempo tiempo;

    /**
     * El tour de visita.
     */
    private final Tour tour;

    /**
     * El shop del parque.
     */
    private final Shop shop;

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

        tiempo = new Tiempo();
        tour = new Tour();
        shop = new Shop();
        carreraGomones = new CarreraGomones();
        faroMirador = new FaroMirador();
        snorkel = new Snorkel();
        nadoDelfines = new NadoDelfines(tiempo, new int[] {12, 15});
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
     * Devuelve el tour del parque.
     *
     * @return el tour
     */
    public synchronized Tour getTour() {
        return tour;
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
        finalizado = true;
        vista.printParque("<<PARQUE CERRADO>>");
    }

    /**
     * Devuelve verdadero si el parque abrió y cerró, de lo contrario falso.
     *
     * @return verdadero si el parque abrió y cerró, de lo contrario falso
     */
    public synchronized boolean finalizado() {
        return finalizado;
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
     * Entra al parque a través de los molinetes.
     *
     * @throws InterruptedException
     */
    public boolean entrar() throws InterruptedException {
        boolean entra = false;
        String visitante = Thread.currentThread().getName();

        synchronized (this) {
            if (!finalizado) {
                vista.printParque(String.format("%s llega a los molinetes", visitante));

                while ((!finalizado && !abierto) || molinetes == 0)
                    this.wait(Tiempo.enMinutos(15));

                molinetes--;
                vista.agregarVisitanteMolinete();
                entra = true;
            }
        }

        if (entra) {
            Thread.sleep(Tiempo.entreMinutos(5, 10));

            synchronized (this) {
                molinetes++;
                visitantes++;
                this.notifyAll();

                vista.printParque(String.format("%s entra al parque", visitante));
                vista.sacarVisitanteMolinete();
                vista.agregarVisitante();
            }
        }

        return entra;
    }

    /**
     * Visitante sale del parque.
     */
    public synchronized void salir() {
        vista.printParque(String.format("%s sale del parque", Thread.currentThread().getName()));
        vista.sacarVisitante();
        visitantes--;
    }

}
