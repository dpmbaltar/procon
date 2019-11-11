package procon.tpof2019;

/**
 * Encargado de indicar en cual tobogán descender del Faro-Mirador.
 */
public class AdministradorTobogan implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public AdministradorTobogan(Parque parque) {
        this.parque = parque;
    }

    /**
     * Abre y cierra el parque según los horarios.
     */
    @Override
    public void run() {
        try {
            while (true) {
                parque.asignarTobogan();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
