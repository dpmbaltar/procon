package procon.tp05.e04;

import java.util.concurrent.Semaphore;

public class GestionaTraficoConSemaforo implements GestionaTrafico {

    private static int TOLERANCIA = 3;
    private int esperanDesdeNorte = 0;
    private int esperanDesdeSur = 0;
    private int seguidosDesdeNorte = 0;
    private int seguidosDesdeSur = 0;
    private int coche = 0;
    private Semaphore desdeNorte = new Semaphore(1, true);
    private Semaphore desdeSur = new Semaphore(1, true);
    private Semaphore pasar = new Semaphore(1, true);
    private Semaphore mutex = new Semaphore(1);

    @Override
    public void entrarCocheDelNorte(int id) throws InterruptedException {
        System.out.println("Coche "+id+" llega desde el Norte.");
        desdeNorte.acquire();
        //System.out.println("Coche "+id+" es el siguiente desde el Norte.");
        pasar.acquire();
        mutex.acquire();
        System.out.println("Coche "+id+" entra desde el Norte.");
        coche = id;
        seguidosDesdeNorte++;
        seguidosDesdeSur = 0;
        mutex.release();
    }

    @Override
    public void entrarCocheDelSur(int id) throws InterruptedException {
        System.out.println("Coche "+id+" llega desde el Sur.");
        desdeSur.acquire();
        //System.out.println("Coche "+id+" es el siguiente desde el Sur.");
        pasar.acquire();
        mutex.acquire();
        System.out.println("Coche "+id+" entra desde el Sur.");
        coche = id;
        seguidosDesdeSur++;
        seguidosDesdeNorte = 0;
        mutex.release();
    }

    @Override
    public void salirCocheDelNorte() throws InterruptedException {
        mutex.acquire();
        System.out.println("Coche "+coche+" sale desde el Norte.");
        coche = 0;

        if (seguidosDesdeNorte >= TOLERANCIA) {
            System.out.println(TOLERANCIA+" seguidos desde el Norte. Le toca al Sur");
            seguidosDesdeNorte = 0;
            desdeSur.release();
            //desdeNorte.release();
        } else {
            desdeNorte.release();
            //desdeSur.release();
        }

        mutex.release();
        pasar.release();
    }

    @Override
    public void salirCocheDelSur() throws InterruptedException {
        mutex.acquire();
        System.out.println("Coche "+coche+" sale desde el Sur.");
        coche = 0;

        if (seguidosDesdeSur >= TOLERANCIA) {
            System.out.println(TOLERANCIA+" seguidos desde el Sur. Le toca al Norte");
            seguidosDesdeSur = 0;
            desdeNorte.release();
            //desdeSur.release();
        } else {
            desdeSur.release();
            //desdeNorte.release();
        }

        mutex.release();
        pasar.release();
    }

}
