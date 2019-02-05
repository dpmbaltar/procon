package procon.tp03.e06;

import java.util.concurrent.Semaphore;

public class SalaFumadoresSemaphore implements SalaFumadores {

    private final Semaphore semAgente = new Semaphore(1);
    private final Semaphore semFumador1 = new Semaphore(0);
    private final Semaphore semFumador2 = new Semaphore(0);
    private final Semaphore semFumador3 = new Semaphore(0);
    private int idFumador;

    public SalaFumadoresSemaphore() {
        this.idFumador = 0;
    }

    @Override
    public void colocar(int ingredientesParaFumador) {
        try {
            // Esperar mientras un fumador esté fumando
            semAgente.acquire();

            // Colocar los ingredientes para el fumador que corresponda
            idFumador = ingredientesParaFumador;
            System.out
                    .println("Colocado ingredientes para fumador " + idFumador);
            if (idFumador == 1)
                semFumador1.release();
            if (idFumador == 2)
                semFumador2.release();
            if (idFumador == 3)
                semFumador3.release();
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void entraFumar(int idFumador) {
        try {
            // Esperar hasta que estén los ingredientes para el fumador dado
            if (idFumador == 1)
                semFumador1.acquire();
            if (idFumador == 2)
                semFumador2.acquire();
            if (idFumador == 3)
                semFumador3.acquire();

            System.out.println("Fumador " + idFumador + " empieza a fumar.");
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void terminaFumar() {
        System.out.println("Fumador " + idFumador + " termina de fumar.");
        idFumador = 0;
        semAgente.release();
    }

}
