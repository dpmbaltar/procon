package procon.tp05.e03;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Recinto recinto = new RecintoMonitor();
        for (int i = 1; i <= 12; i++) {
            (new Thread(new Soldado(recinto), "Soldado-" + i)).start();
            /*try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(2, 4) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

}
