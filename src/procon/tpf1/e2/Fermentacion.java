package procon.tpf1.e2;

public class Fermentacion implements Runnable {

    private final Miembro miembro;
    private final UnidadFermentacion uf;
    private boolean finalizada = false;

    public Fermentacion(Miembro m, UnidadFermentacion uf) {
        this.miembro = m;
        this.uf = uf;
    }

    public boolean estaFinalizada() {
        return finalizada;
    }

    @Override
    public void run() {
        try {
            uf.iniciarFermentacion();
            Thread.sleep(2000);
            uf.finalizarFermentacion();
            finalizada = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
