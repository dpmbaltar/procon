package procon.tpf1.e2;

/**
 * Unidad de fermentación.
 */
public class UnidadFermentacion {

    private final int id;
    private Miembro miembro;
    private Vino vino;

    public UnidadFermentacion(int id) {
        this.id = id;
    }

    public synchronized int getId() {
        return id;
    }
    
    public synchronized Vino getVino() {
        return vino;
    }

    public synchronized void ocupar(Miembro miembro) {
        this.miembro = miembro;
    }

    public synchronized void desocupar() {
        this.miembro = null;
        this.vino = null;
    }

    public synchronized boolean estaOcupada() {
        return this.miembro != null;
    }

    public synchronized void iniciarFermentacion() {
        System.out.println(miembro.getNombre() + ">>> inicia fermentación");
    }

    public synchronized void finalizarFermentacion() {
        vino = new Vino(miembro);
        System.out.println(miembro.getNombre() + ">>> finaliza fermentación");
    }

}
