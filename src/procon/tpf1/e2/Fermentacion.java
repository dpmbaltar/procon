package procon.tpf1.e2;

/**
 * Representa la fermentación del vino de un miembro.
 */
public class Fermentacion implements Runnable {

    private final Miembro miembro;
    private final UnidadFermentacion unidadFermentacion;
    private boolean completa = false;

    public Fermentacion(Miembro m, UnidadFermentacion uf) {
        this.miembro = m;
        this.unidadFermentacion = uf;
    }

    public int getIdUnidadFermentacion() {
        return unidadFermentacion.getId();
    }

    public boolean estaCompleta() {
        return completa;
    }

    @Override
    public void run() {
        try {
            if (!completa) {
                unidadFermentacion.iniciarFermentacion();
                Thread.sleep(2000);
                unidadFermentacion.finalizarFermentacion();
                completa = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
