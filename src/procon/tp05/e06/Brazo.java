package procon.tp05.e06;

import java.util.Random;

public class Brazo implements Runnable {

    private final Mostrador mostrador;

    public Brazo(Mostrador mostrador) {
        this.mostrador = mostrador;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            while (true) {
                mostrador.retirarCaja();
                Thread.sleep(random.nextInt(10) * 100 + 1000);
                mostrador.reponerCaja();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
