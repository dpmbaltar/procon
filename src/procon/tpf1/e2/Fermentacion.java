package procon.tpf1.e2;

/**
 * Representa la fermentación del vino de un miembro del club.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Fermentacion implements Runnable {

    /**
     * El miembro que fermenta.
     */
    private final Miembro miembro;

    /**
     * La unidad de fermentación utilizada.
     */
    private final UnidadFermentacion unidadFermentacion;

    /**
     * Si la fermentación está completa.
     */
    private boolean completa = false;

    /**
     * Constructor con el miembro y unidad de fermentación.
     * 
     * @param m  el miembro
     * @param uf la unidad de fermentación
     */
    public Fermentacion(Miembro m, UnidadFermentacion uf) {
        miembro = m;
        unidadFermentacion = uf;
    }

    /**
     * Devuelve el miembro que fermenta.
     * 
     * @return el miembro
     */
    public Miembro getMiembro() {
        return miembro;
    }

    /**
     * Devuelve el ID de la unidad de fermentación. No se devuelve la unidad en
     * sí para evitar que un miembro puedad alterar el estado de la misma.
     * 
     * @return
     */
    public int getIdUnidadFermentacion() {
        return unidadFermentacion.getId();
    }

    /**
     * Verifica si la fermentación está completa.
     * 
     * @return verdadero si está completa, falso en caso contrario
     */
    public boolean estaCompleta() {
        return completa;
    }

    /**
     * Inicia la fermentación de un miembro en la unidad de fermentación.
     */
    @Override
    public void run() {
        try {
            if (!completa) {
                unidadFermentacion.iniciarFermentacion();
                Thread.sleep(4000);
                unidadFermentacion.finalizarFermentacion();
                completa = true;
            }
        } catch (InterruptedException e) {
            System.out.println(
                    miembro.getNombre() + ">>> fermentación interrumpida");
        }
    }

}
