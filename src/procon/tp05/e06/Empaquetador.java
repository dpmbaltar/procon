package procon.tp05.e06;

import java.util.Random;

public class Empaquetador implements Runnable {

    private final Mostrador mostrador;

    public Empaquetador(Mostrador mostrador) {
        this.mostrador = mostrador;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            int peso = 0;
            while (true) {
                peso = mostrador.tomarPastel();
                Thread.sleep(random.nextInt(10) * 100 + 1000);
                mostrador.soltarPastel(peso);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
