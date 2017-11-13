package procon.tp05.e04;

import java.util.concurrent.Semaphore;

public class GestionaTraficoConSemaforo implements GestionaTrafico {

    private int ultimosDesdeNorte = 0;
    private int ultimosDesdeSur = 0;
    private Semaphore desdeNorte = new Semaphore(1, true);
    private Semaphore desdeSur = new Semaphore(1, true);
    private Semaphore pasar = new Semaphore(1, true);

    @Override
    public void entrarCocheDelNorte(int id) throws InterruptedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void entrarCocheDelSur(int id) throws InterruptedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void salirCocheDelNorte() {
        // TODO Auto-generated method stub

    }

    @Override
    public void salirCocheDelSur() {
        // TODO Auto-generated method stub

    }

}
