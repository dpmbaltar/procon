package procon.tpf1.e2;

/**
 * Representa una unidad de fermentación.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class UnidadFermentacion {

    /**
     * El ID de la unidad.
     */
    private final int id;

    /**
     * Indica si una fermentación fue finalizada.
     */
    private boolean finalizada = false;

    /**
     * EL miembro que ocupa la unidad.
     */
    private Miembro miembro;

    /**
     * El vino fabricado en la unidad.
     */
    private Vino vino;

    /**
     * Constructor con el ID de la unidad.
     * 
     * @param id el ID de la unidad
     */
    public UnidadFermentacion(int id) {
        this.id = id;
    }

    /**
     * Devuelve el ID de la unidad.
     * 
     * @return el ID de la unidad
     */
    public synchronized int getId() {
        return id;
    }

    /**
     * Devuelve el vino fabricado. Si aun no se ha finalizado una fermentación,
     * entonces devuelve nulo.
     * 
     * @return el vino fabricado
     */
    public synchronized Vino getVino() {
        return vino;
    }

    /**
     * Ocupa la unidad por un miembro.
     * 
     * @param miembro el miembro a ocupar la unidad
     */
    public synchronized void ocupar(Miembro miembro) {
        this.miembro = miembro;
    }

    /**
     * Desocupa la unidad.
     */
    public synchronized void desocupar() {
        finalizada = false;
        miembro = null;
        vino = null;
    }

    /**
     * Verifica si la unidad está ocupada.
     * 
     * @return verdadero si está ocupada, falso en caso contrario
     */
    public synchronized boolean estaOcupada() {
        return miembro != null;
    }

    /**
     * Inicia la fermentación de un vino.
     */
    public synchronized void iniciarFermentacion() {
        if (!finalizada)
            System.out.println(miembro.getNombre() + ">>> inicia fermentación");
    }

    /**
     * Finaliza la fermentación de un vino.
     */
    public synchronized void finalizarFermentacion() {
        if (!finalizada) {
            vino = new Vino(miembro);
            finalizada = true;
            System.out
                    .println(miembro.getNombre() + ">>> finaliza fermentación");
        }
    }

}
