/*
 * Trabajo Práctico Obligatorio 01
 * Ejercicio 01
 */
package procon.tp02.e07;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Hamaca de descanso de los hamsters.
 *
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Hamaca {

    /**
     * El semaforo de la hamaca.
     */
    private Semaphore semaforo;

    /**
     * Constructor.
     */
    public Hamaca() {
        semaforo = new Semaphore(1, true);
    }

    /**
     * Simula el uso de la hamaca (puede tardar de 1 a 3 segundos).
     */
    public void usar() {
        int segundos = ThreadLocalRandom.current().nextInt(10, 30) * 100;
        String hamster = Thread.currentThread().getName();

        try {
            semaforo.acquire();
            System.out.println(
                    "El hamster " + hamster + " comienza a descansar...");
            System.out.println("El hamster " + hamster
                    + " está descansando en la hamaca...");
            Thread.sleep(segundos);
            System.out.println(
                    "El hamster " + hamster + " termina de descansar...");
            semaforo.release();
        } catch (InterruptedException e) {
            System.out.println("¡El hamster " + hamster + " fue interrumpido!");
        }
    }
}
