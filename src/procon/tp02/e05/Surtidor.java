package procon.tp02.e05;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Surtidor {

    private Semaphore sem = new Semaphore(1, true);

    public void usar(Auto auto) {
        try {
            sem.acquire();
            System.out.println("El " + auto.toString() + " está recargando...");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 100);
            auto.setKmFaltantesParaElService(auto.getKmTanque());
            System.out.println("El " + auto.toString() + " ha sido recargado.");
            sem.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
