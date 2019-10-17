package procon.tp05.e03;

import java.util.concurrent.ThreadLocalRandom;

public class Soldado implements Runnable {

    private final Recinto recinto;
    private boolean quierePostre;
    private boolean quiereRefresco;

    public Soldado(Recinto recinto) {
        this.recinto = recinto;
        quiereRefresco = ThreadLocalRandom.current().nextBoolean();
        quierePostre = ThreadLocalRandom.current().nextBoolean();
    }

    @Override
    public void run() {
        int nroMostradorComida;
        int nroMostradorPostre;
        Bandeja bandeja;

        try {
            // Soldado entra al recinto
            recinto.entrar();

            // Pedir bandeja
            nroMostradorComida = recinto.pedirBandeja(quiereRefresco);
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
            bandeja = recinto.tomarBandeja(nroMostradorComida);

            // Si la bandeja tiene refresco, toma un abridor y lo utiliza
            if (bandeja.tieneRefresco()) {
                recinto.tomarAbridor();
                Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
                recinto.dejarAbridor();
            }

            // Soldado come su comida
            System.out.println(Thread.currentThread().getName() + ">>> come su comida");
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);

            // Si quiere postre, lo pide en uno de los 3 mostradores
            if (quierePostre) {
                nroMostradorPostre = recinto.pedirPostre();
                Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
                recinto.tomarPostre(nroMostradorPostre);

                // Come su postre
                System.out.println(Thread.currentThread().getName()+ ">>> come su postre");
                Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
            }

            recinto.salir();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
