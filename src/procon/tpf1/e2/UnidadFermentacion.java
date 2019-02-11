package procon.tpf1.e2;

/**
 * Unidad de fermentación.
 */
public class UnidadFermentacion {

    private final int id;
    private Miembro miembro;

    public UnidadFermentacion(int id) {
        this.id = id;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void ocupar(Miembro miembro) {
        this.miembro = miembro;
    }

    public synchronized void desocupar() {
        this.miembro = null;
    }

    public synchronized boolean estaOcupada() {
        return this.miembro != null;
    }

    public synchronized void iniciarFermentacion() {
        System.out.println(miembro.getNombre() + ">>> inicia fermentación");
    }

    public synchronized void finalizarFermentacion() {
        System.out.println(miembro.getNombre() + ">>> finaliza fermentación");
    }

}
