package procon.tp05.e06;

import java.util.Random;

public class Horno implements Runnable {

    private final Mostrador mostrador;
    private final int pesoMinimo;

    public Horno(Mostrador mostrador, int pesoMinimo) {
        this.mostrador = mostrador;
        this.pesoMinimo = pesoMinimo;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            while (true) {
                mostrador.colocarPastel(random.nextInt(20) * 100 + pesoMinimo);
                Thread.sleep(random.nextInt(20) * 100 + 2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
