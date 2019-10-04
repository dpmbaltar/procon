package procon.tp03.e06;

import java.util.concurrent.Semaphore;

public class SalaFumadoresSemaphore implements SalaFumadores {

    /* Semáforo para el agente */
    private final Semaphore semAgente = new Semaphore(1);

    /* Semáforos para los fumadores */
    private final Semaphore semFumador1 = new Semaphore(0);
    private final Semaphore semFumador2 = new Semaphore(0);
    private final Semaphore semFumador3 = new Semaphore(0);
    private int idFumador;

    /* Constructor */
    public SalaFumadoresSemaphore() {
        this.idFumador = 0;
    }

    @Override
    public void colocar(int ingredientesParaFumador) {
        try {
            // Esperar si un fumador está fumando
            semAgente.acquire();

            // Colocar los ingredientes para el fumador que corresponda
            idFumador = ingredientesParaFumador;
            System.out.println("Colocado ingredientes para fumador " + idFumador);

            switch (idFumador) {
                case 1:
                    semFumador1.release();
                    break;
                case 2:
                    semFumador2.release();
                    break;
                case 3:
                    semFumador3.release();
                    break;
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void entraFumar(int idFumador) {
        try {
            // Esperar hasta que estén los ingredientes para el fumador dado
            switch (idFumador) {
                case 1:
                    semFumador1.acquire();
                    break;
                case 2:
                    semFumador2.acquire();
                    break;
                case 3:
                    semFumador3.acquire();
                    break;
            }

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
