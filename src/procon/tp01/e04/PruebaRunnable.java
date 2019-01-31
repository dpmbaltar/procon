package procon.tp01.e04;

import java.util.concurrent.ThreadLocalRandom;

/**
 * (e) A medida que se agregó una sentencia repetitiva grande y luego con un
 * tiempo de espera en cada iteración, se pudo observar que los hilos
 * de ejecución se alternan cada vez más, y a su vez es impredecible cual será
 * el orden de ejecución de los hilos.
 */
public class PruebaRunnable {
    public static void main(String[] args) {
        // Dos objetos definen los métodos run
        PingPong o1 = new PingPong("PING", 33);
        PingPong o2 = new PingPong("PONG", 11);
        // Se crean los hilos
        Thread t1 = new Thread(o1);
        Thread t2 = new Thread(o2);
        // Se activan los hilos
        t1.start();
        t2.start();
        for (int i = 0; i < 99; i++) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("HAHA");
        }
    }
}
