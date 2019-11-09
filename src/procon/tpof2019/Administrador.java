package procon.tpof2019;

/**
 * Encargado de abrir y cerrar el parque de acorde a los horarios establecidos.
 */
public class Administrador implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Administrador(Parque parque) {
        this.parque = parque;
    }

    /**
     * Abre y cierra el parque según los horarios.
     */
    @Override
    public void run() {
        try {
            parque.abrir();
            Thread.sleep((Parque.HORA_CIERRA_ACTIVIDADES - Parque.HORA_ABRE) * 1000);
            parque.cerrarActividades();
            Thread.sleep((Parque.HORA_CIERRA - Parque.HORA_CIERRA_ACTIVIDADES) * 1000);
            parque.cerrar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
