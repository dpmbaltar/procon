package procon.clase04.barberia;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barberia {

    public static final int ASIENTOS = 10;
    private final ReentrantLock bloqueo = new ReentrantLock();
    private final Condition clienteSentado = bloqueo.newCondition();
    private final Condition corteFinalizado = bloqueo.newCondition();
    private final Condition barberoDurmiendo = bloqueo.newCondition();
    private final Condition serSiguiente = bloqueo.newCondition();

    /**
     * La persona sentada actualmente en la silla de corte.
     */
    private Persona sentado;
    private ArrayDeque<Persona> esperando;

    public Barberia() {
        esperando = new ArrayDeque<>();
    }

    /**
     * El cliente entra a la barbería y: - Si está lleno, se va (el método
     * retorna sin modificar el estado). - Si hay un cliente sentado, espera a
     * ser atendido. - Si el barbero está sentado, lo despierta y se sienta para
     * ser atendido.
     * 
     * @param cliente
     * @throws InterruptedException
     */
    public void entrar(Cliente cliente) throws InterruptedException {
        bloqueo.lock();
        try {
            if (esperando.size() >= ASIENTOS) {
                System.out.println(
                        cliente.getNombre() + " ve la barbería llena y se va.");
            } else if (esperando.size() > 0) {
                esperando.add(cliente);
                while (!esperando.peek().equals(cliente)) {
                    System.out.println(
                            cliente.getNombre() + " espera su turno...");
                    serSiguiente.await();
                }
                sentado = esperando.remove();
                clienteSentado.signal();
            } else {
                if (sentado != null) {
                    esperando.add(cliente);
                    if (sentado.esBarbero()) {
                        System.out.println("¡Barbero duermiendo!");
                        System.out.println(
                                cliente.getNombre() + " despierta al barbero.");
                        barberoDurmiendo.signal();
                    }
                    while (sentado != null
                            || !esperando.peek().equals(cliente)) {
                        System.out.println(
                                cliente.getNombre() + " espera su turno...");
                        serSiguiente.await();
                    }
                    sentado = esperando.remove();
                } else {
                    sentado = cliente;
                }
                clienteSentado.signal();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Esto NUNCA pasará.");
        } finally {
            bloqueo.unlock();
        }
    }

    public void salir(Cliente cliente) throws InterruptedException {
        bloqueo.lock();
        try {
            while (sentado != null && sentado.equals(cliente)) {
                corteFinalizado.await();
            }
            sentado = null;
            System.out.println(cliente.getNombre() + " se fue.");
            if (!esperando.isEmpty()) {
                serSiguiente.signalAll();
            }
        } finally {
            bloqueo.unlock();
        }
    }

    /**
     * El barbero inicia un corte. Si no hay al menos un cliente en la barbería,
     * entonces se sienta en la silla y duerme.
     * 
     * @param barbero
     * @throws InterruptedException
     */
    public void iniciarCorte(Barbero barbero) throws InterruptedException {
        bloqueo.lock();
        try {
            // Si no hay clientes, sentarse a dormir
            if (esperando.isEmpty()) {
                // Sentarse a si no hay clientes
                if (sentado == null) {
                    sentado = barbero;
                }
                // Dormir hasta que llegue un cliente
                while (sentado != null && sentado.esBarbero()
                        && esperando.isEmpty()) {
                    barberoDurmiendo.await();
                }
                System.out.println("Barbero se despierta.");
                sentado = null;
                serSiguiente.signalAll();
                System.out.println("Barbero llama al siguiente cliente.");
            }
            // Esperar a que se siente el próximo cliente
            while (sentado == null) {
                System.out.println("Barbero espera que se siente el cliente.");
                clienteSentado.await();
            }
            System.out.println("Barbero inicia corte a " + sentado.getNombre());
        } finally {
            bloqueo.unlock();
        }
    }

    /**
     * El barbero finaliza un corte. Si hay alguien esperando un corte, espera a
     * que se siente.
     * 
     * @throws InterruptedException
     */
    public void finalizarCorte() throws InterruptedException {
        bloqueo.lock();
        try {
            System.out
                    .println("Barbero finaliza corte a " + sentado.getNombre());
            corteFinalizado.signal();
        } finally {
            bloqueo.unlock();
        }
    }
}
