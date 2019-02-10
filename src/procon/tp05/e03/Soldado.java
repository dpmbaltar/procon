package procon.tp05.e03;

import java.util.concurrent.ThreadLocalRandom;

public class Soldado implements Runnable {

    private final Recinto recinto;

    public Soldado(Recinto recinto) {
        this.recinto = recinto;
    }

    @Override
    public void run() {
        boolean quierePostre;
        boolean quiereRefresco;
        int nroMostradorComida;
        int nroMostradorPostre;
        Bandeja bandeja;
        try {
            recinto.entrar();
            quiereRefresco = ThreadLocalRandom.current().nextBoolean();
            nroMostradorComida = recinto.pedirBandeja(quiereRefresco);
            Thread.sleep(ThreadLocalRandom.current().nextInt(2, 4) * 100);
            bandeja = recinto.tomarBandeja(nroMostradorComida);

            if (bandeja.tieneRefresco()) {
                recinto.tomarAbridor();
                Thread.sleep(100);
                recinto.dejarAbridor();
            }

            System.out.println(
                    Thread.currentThread().getName() + ">>> come su comida");
            Thread.sleep(ThreadLocalRandom.current().nextInt(4, 8) * 100);
            quierePostre = ThreadLocalRandom.current().nextBoolean();

            if (quierePostre) {
                nroMostradorPostre = recinto.pedirPostre();
                Thread.sleep(ThreadLocalRandom.current().nextInt(2, 4) * 100);
                recinto.tomarPostre(nroMostradorPostre);
                System.out.println(Thread.currentThread().getName()
                        + ">>> come su postre");
                Thread.sleep(ThreadLocalRandom.current().nextInt(2, 4) * 100);
            }

            recinto.salir();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
