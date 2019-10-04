package procon.tp03.e06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SalaFumadoresLock implements SalaFumadores {

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition ingredientesColocados = lock.newCondition();
    private final Condition salaDesocupada = lock.newCondition();
    private int idFumador;

    public SalaFumadoresLock() {
        this.idFumador = 0;
    }

    @Override
    public void colocar(int ingredientesParaFumador) {
        lock.lock();
        try {
            // Esperar mientras un fumador esté fumando
            while (idFumador > 0)
                salaDesocupada.await();

            // Colocar los ingredientes para el fumador que corresponda
            idFumador = ingredientesParaFumador;
            System.out.println("Colocado ingredientes para fumador " + idFumador);
            ingredientesColocados.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void entraFumar(int idFumador) {
        lock.lock();
        try {
            // Esperar hasta que estén los ingredientes para el fumador dado
            while (this.idFumador != idFumador)
                ingredientesColocados.await();

            System.out.println("Fumador " + idFumador + " empieza a fumar.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void terminaFumar() {
        lock.lock();
        try {
            System.out.println("Fumador " + idFumador + " termina de fumar.");
            idFumador = 0;
            salaDesocupada.signal();
        } finally {
            lock.unlock();
        }
    }

}
