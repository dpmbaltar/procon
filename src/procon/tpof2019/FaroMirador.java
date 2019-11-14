package procon.tpof2019;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Faro-Mirador con vista a 40 m de altura y descenso en tobogán.
 * Implementado con BlockingQueue.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class FaroMirador {

    /**
     * Se utiliza para asignar toboganes según el orden de llegada de los visitantes.
     */
    private final BlockingQueue<Integer> descenso = new SynchronousQueue<>(true);

    /**
     * Tobogán 1.
     */
    private final BlockingQueue<String> tobogan1 = new ArrayBlockingQueue<>(1, true);

    /**
     * Tobogán 2.
     */
    private final BlockingQueue<String> tobogan2 = new ArrayBlockingQueue<>(1, true);

    /**
     * Escalera (hasta 10 visitantes a la vez, en orden de llegada).
     */
    private final BlockingQueue<String> escaleraFaroMirador = new ArrayBlockingQueue<>(10, true);

    /**
     * Cola de visitantes para el Faro-Mirador.
     */
    private final BlockingQueue<String> colaFaroMirador = new ArrayBlockingQueue<>(1, true);

    /**
     * Cantidad de descensos realizados.
     */
    private int cantidadDescensos = 0;

    private final VistaParque vista = VistaParque.getInstance();

    /**
     * Devuelve verdadero si hay visitantes, falso en caso contrario.
     */
    public synchronized boolean hayVisitantes() {
        return !escaleraFaroMirador.isEmpty();
    }

    /**
     * Asigna un tobogan al próximo visitante en querer descender en tobogán.
     *
     * @throws InterruptedException
     */
    public void asignarTobogan() throws InterruptedException {
        descenso.put(cantidadDescensos % 2);
        cantidadDescensos++;
    }

    /**
     * Ir a la actividad Faro-Mirador.
     *
     * @throws InterruptedException
     */
    public void iniciarActividad() throws InterruptedException {
        String visitante = Thread.currentThread().getName();
        vista.printFaroMirador(String.format("%s va al Faro-Mirador", visitante));
        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
        colaFaroMirador.put(visitante);
    }

    /**
     * Inicia ascenso al faro por la escalera.
     *
     * @throws InterruptedException
     */
    public void iniciarAscensoPorEscalera() throws InterruptedException {
        String visitante = Thread.currentThread().getName();
        escaleraFaroMirador.put(visitante);
        vista.printFaroMirador(String.format("%s inicia ascenso", visitante));
        colaFaroMirador.poll();
        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
    }

    /**
     * Finaliza ascenso al faro por escalera y observa desde lo alto del Faro-Mirador.
     *
     * @throws InterruptedException
     */
    public void finalizarAscensoPorEscalera() throws InterruptedException {
        synchronized (this) {
            String visitante = Thread.currentThread().getName();

            // Respetar orden de ascenso
            while (!visitante.contentEquals(escaleraFaroMirador.peek()))
                this.wait();

            vista.printFaroMirador(String.format("%s termina ascenso. Observa desde lo alto...", visitante));
            escaleraFaroMirador.poll();
            this.notifyAll();
        }

        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
    }

    /**
     * Inicia descenso en tobogán de un visitante.
     *
     * @return el tobogán asignado
     * @throws InterruptedException
     */
    public int iniciarDescensoEnTobogan() throws InterruptedException {
        int tobogan = descenso.take();
        String visitante = Thread.currentThread().getName();

        vista.printFaroMirador(String.format("%s es asignado el tobogan %d", visitante, tobogan));

        // Utilizar el tobogán asignado, cuando esté disponible
        if (tobogan == 0)
            tobogan1.put(visitante);
        else if (tobogan == 1)
            tobogan2.put(visitante);

        vista.printFaroMirador(String.format("%s inicia descenso en el tobogan %d", visitante, tobogan));
        Thread.sleep(1000);

        return tobogan;
    }

    /**
     * Finaliza el descenso en tobogán de un visitante.
     *
     * @param tobogan el tobogán asignado
     * @throws InterruptedException
     */
    public void finalizarDescensoEnTobogan(int tobogan) throws InterruptedException {
        String visitante = Thread.currentThread().getName();
        vista.printFaroMirador(String.format("%s termina descenso en el tobogan %d", visitante, tobogan));

        // Liberar el tobogán utilizado
        if (tobogan == 0)
            visitante = tobogan1.take();
        else if (tobogan == 1)
            visitante = tobogan2.take();

        vista.printFaroMirador(String.format("%s vuelve del Faro-Mirador", visitante));
    }

}
