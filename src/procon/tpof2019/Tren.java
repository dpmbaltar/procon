package procon.tpof2019;

/**
 * Encargado de llevar y traer visitantes a la actividad "Carrera de gomones por el río".
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Tren implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Tren(Parque parque) {
        this.parque = parque;
    }

    /**
     * Lleva y trae visitantes a la actividad.
     */
    @Override
    public void run() {
        CarreraGomones carrera = parque.getCarreraGomones();

        /*try {
            while (parque.estaAbierto()) {
                //TODO: implementar Tren
                //carrera.traerVisitantes();
//                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
                //carrera.volver();
//                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }*/
    }

}
