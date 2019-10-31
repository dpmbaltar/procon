package procon.tp05.e06;

import java.util.concurrent.Semaphore;

public class Mostrador {

    /* Peso máximo (gramos) */
    public static final int PESO_MAXIMO = 5000;

    /* Peso del pastel actual (gramos) */
    private int pesoPastel = 0;
    private int pesoCaja = 0;
    private int cantidadPastelesCaja = 0;

    private final Semaphore mostrador = new Semaphore(1, true);
    private final Semaphore pastel = new Semaphore(0);
    private final Semaphore caja = new Semaphore(1);
    private final Semaphore reponer = new Semaphore(0);

    private final Semaphore mutex = new Semaphore(1);

    public void colocarPastel(int peso) throws InterruptedException {
        mostrador.acquire();
        pesoPastel = peso;
        System.out.println(String.format("Pastel de %d g colocado (%s)", pesoPastel, Thread.currentThread().getName()));
        pastel.release();
    }

    public void retirarCaja() throws InterruptedException {
        reponer.acquire();
        System.out.println(String.format("Caja retirada de %d g con %d pastel(es)", pesoCaja, cantidadPastelesCaja));
        pesoCaja = 0;
        cantidadPastelesCaja = 0;
    }

    public void reponerCaja() {
        System.out.println("Caja vacía colocada en mostrador");
        caja.release();
    }

    public int tomarPastel() throws InterruptedException {
        int peso;

        pastel.acquire();
        peso = pesoPastel;
        pesoPastel = 0;
        System.out.println(String.format("%s toma pastel de %d g", Thread.currentThread().getName(), peso));
        mostrador.release();

        return peso;
    }

    public void soltarPastel(int peso) throws InterruptedException {
        caja.acquire();

        if ((pesoCaja + peso) > PESO_MAXIMO) {
            reponer.release();
            caja.acquire();
        }

        System.out.println(String.format("%s suelta pastel de %d g", Thread.currentThread().getName(), peso));
        pesoCaja += peso;
        cantidadPastelesCaja++;
        caja.release();
    }
}
