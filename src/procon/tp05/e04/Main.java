package procon.tp05.e04;

import java.util.concurrent.ThreadLocalRandom;

/**
 * TP05 - Ejercicio 04
 * Puente
 */
public class Main {

    private static final int CANTIDAD_AUTOS = 10;

    public static void main(String[] args) {
        GestionaTrafico gt = new GestionaTraficoMonitor();
        Thread[] autos = new Thread[CANTIDAD_AUTOS];
        char desde[] = { 'N', 'S' };

        for (int i = 0; i < autos.length; i++)
            autos[i] = new Thread(new Auto(i, desde[ThreadLocalRandom.current().nextInt(0, 2)], gt));

        for (int i = 0; i < autos.length; i++) {
            autos[i].start();

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 21) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
