package procon.tpo.filosofos;

import java.util.concurrent.ThreadLocalRandom;

public class Filosofo implements Runnable {

    private Tenedor tenedorIzquierdo;
    private Tenedor tenedorDerecho;

    public Filosofo(Tenedor tenedorIzquierdo, Tenedor tenedorDerecho) {
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
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

    public void pensar() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " está pensando...");
        Thread.sleep(ThreadLocalRandom.current().nextInt(400, 800));
    }

    public void comer() throws InterruptedException {
        //TODO: evitar deadlock
        tenedorIzquierdo.ocupar();
        tenedorDerecho.ocupar();
        System.out.println(Thread.currentThread().getName() + " comienza a comer...");
        Thread.sleep(ThreadLocalRandom.current().nextInt(200, 600));
        System.out.println(Thread.currentThread().getName() + " termina de comer...");
        tenedorIzquierdo.liberar();
        tenedorDerecho.liberar();
    }

}
