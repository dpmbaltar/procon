/*
 * Trabajo Práctico Obligatorio 01
 * Ejercicio 01
 */
package procon.tpo01.e01;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Rueda de ejercicios de los hamsters.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Rueda {

    /**
     * El semaforo de la rueda.
     */
    private Semaphore semaforo;

    /**
     * Constructor.
     */
    public Rueda() {
        semaforo = new Semaphore(1, true);
    }

    /**
     * Simula el uso de la rueda (puede tardar de 1 a 2 segundos).
     */
    public void usar() {
        int segundos = ThreadLocalRandom.current().nextInt(10, 20) * 100;
        String hamster = Thread.currentThread().getName();

        try {
            semaforo.acquire();
            System.out.println(
                    "El hamster " + hamster + " comienza a ejercitarse...");
            System.out.println("El hamster " + hamster
                    + " está ejercitándose en la rueda...");
            Thread.sleep(segundos);
            System.out.println(
                    "El hamster " + hamster + " termina de ejercitarse...");
            semaforo.release();
        } catch (InterruptedException e) {
            System.out.println("¡El hamster " + hamster + " fue interrumpido!");
        }
    }
}
