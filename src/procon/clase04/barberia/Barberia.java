package procon.clase04.barberia;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barberia {

    public static final int ASIENTOS = 3;
    private final ReentrantLock bloqueo = new ReentrantLock();
    private final Condition entraCliente = bloqueo.newCondition();
    private final Condition serSiguiente = bloqueo.newCondition();
    private Persona sentado;
    private ArrayDeque<Persona> esperando;

    public Barberia() {
        esperando = new ArrayDeque<>();
    }

    public void sentarse(Cliente cliente) throws InterruptedException {
        bloqueo.lock();
        try {
            if (esperando.size() >= ASIENTOS) {
                // Barbería llena
            } else if (sentado.esCliente()) {
                esperando.add(cliente);
                while (!esperando.peek().equals(cliente)) {
                    serSiguiente.await();
                }
                sentado = esperando.remove();
            } else if (sentado.esBarbero()) {
                sentado = cliente;
                entraCliente.signal();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Esto NUNCA debería ocurrir.");
        } finally {
            bloqueo.unlock();
        }
    }

    public void iniciarCorte(Barbero barbero) throws InterruptedException {
        bloqueo.lock();
        try {
            while (sentado == null) {
                sentado = barbero;
                entraCliente.await();
            }
            System.out.println("Comienza corte a " + sentado.getNombre());
        } finally {
            bloqueo.unlock();
        }
    }

    public void finalizarCorte() {
        bloqueo.lock();
        try {
            System.out.println("Finaliza corte a " + sentado.getNombre());
            sentado = null;
            serSiguiente.signalAll();
        } finally {
            bloqueo.unlock();
        }
    }
}
