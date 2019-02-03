/*
 * Trabajo Práctico Obligatorio 01
 * Ejercicio 01
 */
package procon.tp02.e07;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Plato de comida de los hamsters.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Plato {

    /**
     * El semaforo del plato.
     */
    private Semaphore semaforo;

    /**
     * Constructor.
     */
    public Plato() {
        semaforo = new Semaphore(1, true);
    }

    /**
     * Simula el uso del plato (puede durar de 0,5 a 1,5 segundos).
     */
    public boolean usar() {
        boolean utilizado = false;
        int segundos = ThreadLocalRandom.current().nextInt(5, 15) * 100;
        String hamster = Thread.currentThread().getName();

        try {
            if (semaforo.tryAcquire()) {
                utilizado = true;
                System.out.println(
                        "El hamster " + hamster + " comienza a comer...");
                System.out.println("El hamster " + hamster
                        + " está comiendo del plato...");
                Thread.sleep(segundos);
                System.out.println(
                        "El hamster " + hamster + " termina de comer...");
                semaforo.release();
            }
        } catch (InterruptedException e) {
            System.out.println("¡El hamster " + hamster + " fue interrumpido!");
        }
        
        return utilizado;
    }
}
