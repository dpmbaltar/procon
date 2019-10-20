package procon.tp05.e04;

import java.util.concurrent.ThreadLocalRandom;

public class Trafico implements Runnable {

    private static int auto = 0;
    private final GestionaTrafico gt;

    public Trafico(GestionaTrafico gt) {
        this.gt = gt;
    }

    private static final synchronized int generarAuto() {
        return ++auto;
    }

    @Override
    public void run() {
        int auto;
        char desde;
        try {
            while (true) {
                auto = generarAuto();
                desde = ThreadLocalRandom.current().nextBoolean() ? 'N' : 'S';

                switch (desde) {
                    case 'N':
                        gt.entrarCocheDelNorte(auto);
                        Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20) * 100);
                        gt.salirCocheDelNorte(auto);
                        break;
                    case 'S':
                        gt.entrarCocheDelSur(auto);
                        Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20) * 100);
                        gt.salirCocheDelSur(auto);
                        break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
