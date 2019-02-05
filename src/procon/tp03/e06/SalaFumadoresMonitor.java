package procon.tp03.e06;

public class SalaFumadoresMonitor implements SalaFumadores {

    private int idFumador;

    public SalaFumadoresMonitor() {
        this.idFumador = 0;
    }

    @Override
    public synchronized void colocar(int ingredientesParaFumador) {
        try {
            // Esperar mientras un fumador esté fumando
            while (idFumador > 0)
                wait();

            // Colocar los ingredientes para el fumador que corresponda
            idFumador = ingredientesParaFumador;
            System.out
                    .println("Colocado ingredientes para fumador " + idFumador);
            notifyAll();
        } catch (InterruptedException e) {
        }
    }

    @Override
    public synchronized void entraFumar(int idFumador) {
        try {
            // Esperar hasta que estén los ingredientes para el fumador dado
            while (this.idFumador != idFumador)
                wait();

            System.out.println("Fumador " + idFumador + " empieza a fumar.");
        } catch (InterruptedException e) {
        }
    }

    @Override
    public synchronized void terminaFumar() {
        System.out.println("Fumador " + idFumador + " termina de fumar.");
        idFumador = 0;
        notifyAll();
    }

}
