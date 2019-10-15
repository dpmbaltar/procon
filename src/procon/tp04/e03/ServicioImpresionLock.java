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

    private final Lock lock = new ReentrantLock();
    private final Condition nuevaImpresionA = lock.newCondition();
    private final Condition nuevaImpresionB = lock.newCondition();
    private final ArrayDeque<Impresion> colaImpresionA = new ArrayDeque<>();
    private final ArrayDeque<Impresion> colaImpresionB = new ArrayDeque<>();

    @Override
    public void solicitar(Impresion impresion) {
        lock.lock();
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
                    // Asignar impresión a la impresora que tiene menos en espera
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
            lock.unlock();
        }
    }

    @Override
    public Impresion imprimir(Impresora impresora) {
        Impresion impresion = null;
        lock.lock();

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
                    System.out.println("Esto nunca debería imprimirse");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return impresion;
    }
}
