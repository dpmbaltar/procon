package procon.tp05.e02;

import java.util.concurrent.Semaphore;

/**
 * Consultar - Escritor no permite casi leer
 */
public class LibroSemaforoBinario implements Libro {

    /* Cantidad de escritores que van a escribir antes de permitir leer a lectores */
    private int cantidadEscritores = 0;
    /* Cantidad de lectores leyendo */
    private int cantidadLeyendo = 0;

    private final Semaphore mutexLectores = new Semaphore(1);
    private final Semaphore mutexEscritores = new Semaphore(1);
    private final Semaphore escritores = new Semaphore(1);
    private final Semaphore lectores = new Semaphore(1);

    @Override
    public void comenzarEscritura() throws InterruptedException {
        mutexEscritores.acquire();
        cantidadEscritores++;

        // Esperar que lectores terminen de leer antes de escribir
        if (cantidadEscritores == 1) {
            lectores.acquire();
        }

        mutexEscritores.release();
        escritores.acquire(); // Esperar, si un escritor está escribiendo

        // Comenzar a escribir
        System.out.println(Thread.currentThread().getName() + "> comienza escritura");
    }

    @Override
    public void comenzarLectura() throws InterruptedException {
        lectores.acquire();
        mutexLectores.acquire();
        cantidadLeyendo++;

        // Esperar que los ecritores terminen de escribir
        if (cantidadLeyendo == 1) {
            escritores.acquire();
        }

        // Comenzar a leer
        System.out.println(Thread.currentThread().getName() + "> comienza lectura");

        mutexLectores.release();
        lectores.release();
    }

    @Override
    public void terminarEscritura() throws InterruptedException {
        // Terminar de escribir
        System.out.println(Thread.currentThread().getName() + "> termina escritura");

        escritores.release();
        mutexEscritores.acquire();
        cantidadEscritores--;

        // Permitir a lectores leer
        if (cantidadEscritores == 0) {
            lectores.release();
        }

        mutexEscritores.release();
    }

    @Override
    public void terminarLectura() throws InterruptedException {
        mutexLectores.acquire();

        // Terminar de leer
        System.out.println(Thread.currentThread().getName() + "> termina lectura");
        cantidadLeyendo--;

        // Si no hay lectores leyendo, permitir escribir
        if (cantidadLeyendo == 0) {
            escritores.release();
        }

        mutexLectores.release();
    }

}
