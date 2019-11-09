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

    private final VistaParque vp = new VistaParque();

    private void mensaje(String mensaje) {
        System.out.println(mensaje);
        vp.mostrar(mensaje);
    }

    /**
     * Abre el parque.
     */
    public synchronized void abrir() {
        abierto = true;
        actividadesAbiertas = true;
        mensaje("<<PARQUE ABIERTO>>");
    }

    /**
     * Cierra las actividades del parque.
     */
    public synchronized void cerrarActividades() {
        actividadesAbiertas = false;
        mensaje("<<ACTIVIDADES CERRADAS>>");
    }

    /**
     * Cierra el parque.
     */
    public synchronized void cerrar() {
        abierto = false;
        mensaje("<<PARQUE CERRADO>>");
    }



}
