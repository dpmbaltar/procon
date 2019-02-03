package procon.tp02.e04;

import java.util.concurrent.Semaphore;

/**
 * El problema de esta clase es que si un hilo no actualiza el valor, el
 * permiso queda bloqueado. Una alternativa a esto es crear el método de la
 * operación deseada en un bloque sincronizado para que la operación sea
 * atómica y no se presenten inconsistencias ni deadlock.
 */
public class Valor {

    private int valor = 3;
    private Semaphore sem = new Semaphore(1, true);

    public int getValor() throws InterruptedException {
        sem.acquire();
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
        sem.release();
    }
}
