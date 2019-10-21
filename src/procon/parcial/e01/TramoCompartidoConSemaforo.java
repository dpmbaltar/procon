package procon.parcial.e01;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class TramoCompartidoConSemaforo implements TramoCompartido {

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore entrarDesdeA = new Semaphore(0, true);
    private final Semaphore entrarDesdeB = new Semaphore(0, true);
    private char puedePasar = 0;

    private final Queue<Object> trenesDesdeA = new LinkedList<>();
    private final Queue<Object> trenesDesdeB = new LinkedList<>();

    @Override
    public void entrarDesdeLadoA(String tren) throws InterruptedException {
        mutex.acquire();

        // Agregar tren a la espera en A
        trenesDesdeA.add(tren);
        System.out.println(String.format("%s llega desde A", tren));

        // Primer tren viene desde A
        if (puedePasar == 0) {
            puedePasar = 'A';
            entrarDesdeA.release();
        }

        mutex.release();
        entrarDesdeA.acquire();

        // Entró desde A
        mutex.acquire();
        trenesDesdeA.remove();
        System.out.println(String.format("Entra %s", tren));
        mutex.release();
    }

    @Override
    public void salirDesdeLadoA(String tren) {
        try {
            mutex.acquire();
            System.out.println(String.format("Sale %s", tren));

            // Decidir si le toca al lado A, B o al que llegue primero si no hay esperando
            if (!trenesDesdeB.isEmpty())
                entrarDesdeB.release();
            else if (!trenesDesdeA.isEmpty())
                entrarDesdeA.release();
            else
                puedePasar = 0;

            mutex.release();
        } catch (InterruptedException e) {}
    }

    @Override
    public void entrarDesdeLadoB(String tren) throws InterruptedException {
        mutex.acquire();

        // Agregar tren a la espera en B
        trenesDesdeB.add(tren);
        System.out.println(String.format("%s llega desde B", tren));

        // Primer tren viene desde B
        if (puedePasar == 0) {
            puedePasar = 'B';
            entrarDesdeB.release();
        }

        mutex.release();
        entrarDesdeB.acquire();

        // Entró desde B
        mutex.acquire();
        trenesDesdeB.remove();
        System.out.println(String.format("Entra %s", tren));
        mutex.release();
    }

    @Override
    public void salirDesdeLadoB(String tren) {
        try {
            mutex.acquire();
            System.out.println(String.format("Sale %s", tren));

            // Decidir si le toca al lado A, B o al que llegue primero si no hay esperando
            if (!trenesDesdeA.isEmpty())
                entrarDesdeA.release();
            else if (!trenesDesdeB.isEmpty())
                entrarDesdeB.release();
            else
                puedePasar = 0;

            mutex.release();
        } catch (InterruptedException e) {}
    }


}
