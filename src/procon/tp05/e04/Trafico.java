package procon.tp05.e04;

import java.util.concurrent.ThreadLocalRandom;

public class Trafico implements Runnable {

    private final char desde;
    private final GestionaTrafico gt;

    public Trafico(char desde, GestionaTrafico gt) {
        this.desde = desde;
        this.gt = gt;
    }

    @Override
    public void run() {
        int auto = 0;

        try {
            while (true) {
                switch (desde) {
                    case 'N':
                        gt.entrarCocheDelNorte(auto);
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 30) * 100);
                        gt.salirCocheDelNorte(auto);
                        break;
                    case 'S':
                        gt.entrarCocheDelSur(auto);
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1, 30) * 100);
                        gt.salirCocheDelSur(auto);
                        break;
                }

                auto++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
