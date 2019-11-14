package procon.tpof2019;

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
    private final Restaurante[] restaurantes = new Restaurante[3];

    /**
     * La actividad "Carrera de gomones por el río".
     */
    private final CarreraGomones carreraGomones = new CarreraGomones();

    /**
     * La actividad "Faro-Mirador con vista a 40 m de altura y descenso en tobogán".
     */
    private final FaroMirador faroMirador = new FaroMirador();

    private final VistaParque vp = VistaParque.getInstance();

    /**
     * Constructor.
     */
    public Parque() {
        int capacidadRestaurantes = 10;

        for (int i = 0; i < restaurantes.length; i++) {
            restaurantes[i] = new Restaurante(i, capacidadRestaurantes);
            capacidadRestaurantes += 5;
        }
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
     * Abre el parque.
     */
    public synchronized void abrir() {
        abierto = true;
        actividadesAbiertas = true;
        vp.printParque("<<PARQUE ABIERTO>>");
    }

    /**
     * Cierra las actividades del parque.
     */
    public synchronized void cerrarActividades() {
        actividadesAbiertas = false;
        vp.printParque("<<ACTIVIDADES CERRADAS>>");
    }

    /**
     * Cierra el parque.
     */
    public synchronized void cerrar() {
        abierto = false;
        vp.printParque("<<PARQUE CERRADO>>");
    }

    public synchronized boolean estaAbierto() {
        return abierto;
    }

    public synchronized boolean actividadesAbiertas() {
        return actividadesAbiertas;
    }

    public void iniciarVisita() {

    }

    /**
     * Finaliza visita en el parque.
     */
    public void finalizarVisita() {
        String visitante = Thread.currentThread().getName();
        vp.printParque(String.format("%s finaliza visita al parque", visitante));
    }

}
