package procon.tp03.e06;

public class SalaFumadoresConMonitor implements SalaFumadores {

    private int idFumador;

    public SalaFumadoresConMonitor() {
        this.idFumador = 0;
    }
    @Override
    public synchronized void colocar(int ingredientesParaFumador) {
        try {
            while (idFumador > 0)
                wait();

            idFumador = ingredientesParaFumador;
            System.out.println("Colocado ingredientes para fumador "+idFumador);
            notifyAll();
        } catch (InterruptedException e) {}
    }

    @Override
    public synchronized void entraFumar(int idFumador) {
        try {
            while (this.idFumador != idFumador)
                wait();

            System.out.println("Fumador "+idFumador+" empieza a fumar.");
        } catch (InterruptedException e) {}
    }

    @Override
    public synchronized void terminaFumar() {
        System.out.println("Fumador "+idFumador+" termina de fumar.");
        idFumador = 0;
        notifyAll();
    }

}
