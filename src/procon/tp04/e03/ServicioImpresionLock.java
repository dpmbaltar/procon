package procon.tp04.e03;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import procon.tp04.e03.Impresora.Tipo;

/**
 * Implementación de un servicio de impresión con Locks.
 */
public class ServicioImpresionLock implements ServicioImpresion {
    private final Lock bloqueo = new ReentrantLock();
    private final Condition nuevaImpresionA = bloqueo.newCondition();
    private final Condition nuevaImpresionB = bloqueo.newCondition();
    private final ArrayDeque<Impresion> colaImpresionA = new ArrayDeque<>();
    private final ArrayDeque<Impresion> colaImpresionB = new ArrayDeque<>();

    public void solicitar(Impresion impresion) {
        bloqueo.lock();
        try {
            switch (impresion.getTipo()) {
            case A:
                colaImpresionA.add(impresion);
                nuevaImpresionA.signalAll();
                break;
            case B:
                colaImpresionB.add(impresion);
                nuevaImpresionB.signalAll();
                break;
            case CUALQUIERA:
                if (colaImpresionA.size() < colaImpresionB.size()) {
                    impresion.setTipo(Tipo.A);
                    colaImpresionA.add(impresion);
                } else {
                    impresion.setTipo(Tipo.B);
                    colaImpresionB.add(impresion);
                }
                break;
            default:
                System.out.println("¡Esto nunca debería ocurrir!");
            }
        } finally {
            bloqueo.unlock();
        }
    }

    public Impresion imprimir(Impresora impresora) {
        Impresion impresion = null;
        bloqueo.lock();
        try {
            switch (impresora.getTipo()) {
            case A:
                while (colaImpresionA.isEmpty())
                    nuevaImpresionA.await();
                impresion = colaImpresionA.remove();
                break;
            case B:
                while (colaImpresionB.isEmpty())
                    nuevaImpresionB.await();
                impresion = colaImpresionB.remove();
                break;
            default:
                System.out.println("¡Esto nunca debería ocurrir!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bloqueo.unlock();
        }

        return impresion;
    }
}
