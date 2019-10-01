package procon.tpo.filosofos;

import java.util.concurrent.ThreadLocalRandom;

public class Filosofo implements Runnable {

    /** Número de filósofo */
    private int numero;

    /** Mesa */
    private Mesa mesa;

    /**
     * Constructor con número y mesa.
     *
     * @param numero para identificar el filósofo
     * @param mesa la mesa
     */
    public Filosofo(int numero, Mesa mesa) {
        this.numero = numero;
        this.mesa = mesa;
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                comer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Simula pensar */
    public void pensar() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " comienza a pensar...");
        Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
        System.out.println(Thread.currentThread().getName() + " termina de pensar...");
    }

    /** Simula comer */
    public void comer() throws InterruptedException {
        if (mesa.tomarTenedores(numero)) {
            System.out.println(Thread.currentThread().getName() + " comienza a comer...");
            Thread.sleep(ThreadLocalRandom.current().nextInt(300, 800));
            System.out.println(Thread.currentThread().getName() + " termina de comer...");
            mesa.dejarTenedores(numero);
        }
    }

}
