package procon.tp05.e04;

import java.util.concurrent.Semaphore;

public class GestionaTraficoSemaforo implements GestionaTrafico {

    private static final int TOLERANCIA = 3;
    private int esperanDesdeNorte = 0;
    private int esperanDesdeSur = 0;
    private int seguidosDesdeNorte = 0;
    private int seguidosDesdeSur = 0;
    private boolean puenteLibre = true;

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore desdeNorte = new Semaphore(0, true);
    private final Semaphore desdeSur = new Semaphore(0, true);

    @Override
    public void entrarCocheDelNorte(int id) throws InterruptedException {
        mutex.acquire();
        System.out.println(String.format("Llega Auto-%d desde NORTE", id));
        esperanDesdeNorte++;
        if (puenteLibre) {
            puenteLibre = false;
            desdeNorte.release();
        }
        mutex.release();

        desdeNorte.acquire();

        mutex.acquire();
        esperanDesdeNorte--;
        seguidosDesdeNorte++;
        System.out.println(String.format("Entra Auto-%d desde NORTE", id));
        mutex.release();
    }

    @Override
    public void entrarCocheDelSur(int id) throws InterruptedException {
        mutex.acquire();
        System.out.println(String.format("Llega Auto-%d desde SUR", id));
        esperanDesdeSur++;
        if (puenteLibre) {
            puenteLibre = false;
            desdeSur.release();
        }
        mutex.release();

        desdeSur.acquire();

        mutex.acquire();
        esperanDesdeSur--;
        seguidosDesdeSur++;
        System.out.println(String.format("Entra Auto-%d desde SUR", id));
        mutex.release();
    }

    @Override
    public void salirCocheDelNorte(int id) throws InterruptedException {
        mutex.acquire();
        System.out.println(String.format("Sale Auto-%d desde NORTE", id));

        if (esperanDesdeSur > 0 && (seguidosDesdeNorte >= TOLERANCIA || esperanDesdeNorte == 0)) {
            if (seguidosDesdeNorte >= TOLERANCIA)
                System.out.println(String.format("!!!Pasaron %d autos desde NORTE!!!", seguidosDesdeNorte));
            seguidosDesdeNorte = 0;
            desdeSur.release();
        } else if (esperanDesdeNorte > 0) {
            desdeNorte.release();
        } else {
            System.out.println("!!!PUENTE LIBRE!!!");
            puenteLibre = true;
        }

        mutex.release();
    }

    @Override
    public void salirCocheDelSur(int id) throws InterruptedException {
        mutex.acquire();
        System.out.println(String.format("Sale Auto-%d desde SUR", id));

        if (esperanDesdeNorte > 0 && (seguidosDesdeSur >= TOLERANCIA || esperanDesdeSur == 0)) {
            if (seguidosDesdeSur >= TOLERANCIA)
                System.out.println(String.format("!!!Pasaron %d autos desde SUR!!!", seguidosDesdeSur));
            seguidosDesdeSur = 0;
            desdeNorte.release();
        } else if (esperanDesdeSur > 0) {
            desdeSur.release();
        } else {
            System.out.println("!!!PUENTE LIBRE!!!");
            puenteLibre = true;
        }

        mutex.release();
    }

}
