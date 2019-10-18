package procon.tp05.e04;

import java.util.concurrent.ThreadLocalRandom;

public class Auto implements Runnable {

    private final int id;
    private final char desde;
    private final GestionaTrafico gestionaTrafico;

    public Auto(int id, char desde, GestionaTrafico gestionaTrafico) {
        this.id = id;
        this.desde = desde;
        this.gestionaTrafico = gestionaTrafico;
    }

    private void entrar() throws InterruptedException {
        switch (desde) {
            case 'N':
                gestionaTrafico.entrarCocheDelNorte(id);
                break;
            case 'S':
                gestionaTrafico.entrarCocheDelSur(id);
                break;
        }
    }

    private void salir() throws InterruptedException {
        switch (desde) {
            case 'N':
                gestionaTrafico.salirCocheDelNorte(id);
                break;
            case 'S':
                gestionaTrafico.salirCocheDelSur(id);
                break;
        }
    }

    private void pasar() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(10, 15) * 100);
    }

    @Override
    public void run() {
        try {
            entrar();
            pasar();
            salir();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
