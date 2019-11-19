package procon.tpof2019;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Faro-Mirador con vista a 40 m de altura y descenso en tobogán.
 * Implementado con BlockingQueue.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class FaroMirador {

    /**
     * Capacidad de la escalera.
     */
    public static final int CAPACIDAD_ESCALERA = 10;

    /**
     * Escalera (hasta {@link procon.tpof2019.FaroMirador#CAPACIDAD_ESCALERA} visitantes a la vez, en orden de llegada).
     */
    private final BlockingQueue<String> escalera = new ArrayBlockingQueue<>(CAPACIDAD_ESCALERA, true);

    /**
     * Se utiliza para asignar toboganes según el orden de llegada de los visitantes.
     */
    private final BlockingQueue<Integer> descenso = new SynchronousQueue<>(true);

    /**
     * Los toboganes.
     */
    @SuppressWarnings("unchecked")
    private final BlockingQueue<String>[] toboganes = new ArrayBlockingQueue[2];

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Constructor.
     */
    public FaroMirador() {
        for (int i = 0; i < toboganes.length; i++)
            toboganes[i] = new ArrayBlockingQueue<String>(1, true);
    }

    /**
     * Devuelve verdadero si hay visitantes, falso en caso contrario.
     */
    public synchronized boolean hayVisitantes() {
        return !escalera.isEmpty();
    }

    /**
     * Asigna un tobogan al próximo visitante que quiera descender en tobogán.
     *
     * @param tobogan si no es 0 o 1, el resultado es: abs(tobogan) mod 2
     * @throws InterruptedException
     */
    public void asignarTobogan(int tobogan) throws InterruptedException {
        descenso.put(0 <= tobogan && tobogan <= 1 ? tobogan : Math.abs(tobogan % 2));
    }

    /**
     * Inicia ascenso al faro por la escalera.
     *
     * Consideración: el orden de los mensajes "Visitante-x inicia ascenso" no está sincronizado debido al método
     * utilizado (BlockingQueue), y puede ser que no coincidan con el orden en el que realmente entran a la escalera,
     * pero en el estado interno de la misma se cumple el orden de llegada, ya que el método put() de escalera está
     * sincronizado, y cuando este llena se bloquearán y despertarán respetando el orden de llegada.
     *
     * @throws InterruptedException
     */
    public void iniciarAscensoPorEscalera() throws InterruptedException {
        String visitante = Thread.currentThread().getName();

        escalera.put(visitante);
        vista.printFaroMirador(String.format("%s inicia ascenso", visitante));
        vista.agregarVisitanteEscalera();

        Thread.sleep(Tiempo.entreMinutos(20, 30));
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
            while (!visitante.contentEquals(escalera.peek()))
                this.wait();

            vista.printFaroMirador(String.format("%s termina ascenso. Observa...", visitante));
            vista.sacarVisitanteEscalera();
            escalera.poll(); // No bloquea el hilo como lo hace el método take()
            this.notifyAll();
        }

        Thread.sleep(Tiempo.entreMinutos(20, 30));
    }

    /**
     * Inicia descenso en tobogán de un visitante.
     *
     * @return el tobogán asignado
     * @throws InterruptedException
     */
    public int iniciarDescensoEnTobogan() throws InterruptedException {
        int tobogan = descenso.take(); // Se bloquea hasta que el administrador del tobogán le asigne uno
        String visitante = Thread.currentThread().getName();

        vista.printFaroMirador(String.format("%s es asignado tobogan %d", visitante, tobogan));

        // Utilizar el tobogán asignado, solo cuando esté desocupado
        toboganes[tobogan].put(visitante);

        vista.ocuparTobogan(tobogan);
        vista.printFaroMirador(String.format("%s inicia descenso en tobogan %d", visitante, tobogan));

        Thread.sleep(Tiempo.entreMinutos(10, 15));

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
        vista.printFaroMirador(String.format("%s termina descenso en tobogan %d", visitante, tobogan));
        vista.desocuparTobogan(tobogan);

        // Liberar el tobogán utilizado (nunca debería bloquearse)
        visitante = toboganes[tobogan].take();
    }

}
