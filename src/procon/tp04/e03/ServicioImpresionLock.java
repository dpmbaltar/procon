package procon.tp04.e03;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServicioImpresionLock implements ServicioImpresion {
    public static enum Tipo {
        A, B;
    }

    private static final int CANTIDAD_A = 3;
    private static final int CANTIDAD_B = 2;
    private int imprimiendoA = 0;
    private int imprimiendoB = 0;
    private final ArrayDeque<Impresion> colaA = new ArrayDeque<>();
    private final ArrayDeque<Impresion> colaB = new ArrayDeque<>();
    private final Lock bloqueo = new ReentrantLock();
    private final Condition libreA = bloqueo.newCondition();
    private final Condition libreB = bloqueo.newCondition();

    public void solicitar(Impresion impresion) {
        bloqueo.lock();
        try {
            switch (impresion.getTipo()) {
            case A:
                colaA.add(impresion);
                while (imprimiendoA >= CANTIDAD_A
                        || !colaA.peek().equals(impresion))
                    libreA.await();
                colaA.remove();
                imprimiendoA++;
                break;
            case B:
                colaB.add(impresion);
                while (imprimiendoB >= CANTIDAD_B
                        || !colaB.peek().equals(impresion))
                    libreB.await();
                colaB.remove();
                imprimiendoB++;
                break;
            default:
                //TODO: (impresion.getTipo() == null) case default
                break;
            }
            System.out.println("Iniciando " + impresion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bloqueo.unlock();
        }
    }

    public void finalizar(Impresion impresion) {
        bloqueo.lock();
        try {
            switch (impresion.getTipo()) {
            case A:
                imprimiendoA--;
                libreA.signalAll();
                break;
            case B:
                imprimiendoB--;
                libreB.signalAll();
                break;
            }
            System.out.println("Finalizado " + impresion);
        } finally {
            bloqueo.unlock();
        }
    }
}
