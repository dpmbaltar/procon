package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Parque {

    /**
     * Hora de abertura el parque.
     */
    public static final int HORA_ABRE = 9;

    /**
     * Hora de cierre del parque.
     */
    public static final int HORA_CIERRA = 18;

    /**
     * Hora de cierre de actividades del parque.
     */
    public static final int HORA_CIERRA_ACTIVIDADES = 17;

    /**
     * Indica si el parque está abierto o cerrado.
     */
    private boolean abierto = false;

    /**
     * Indica si las actividades del parque están abiertas o cerradas.
     */
    private boolean actividadesAbiertas = false;

    private CyclicBarrier trenIda = new CyclicBarrier(15);
    private CyclicBarrier trenVuelta = new CyclicBarrier(15);
    private CyclicBarrier carrera = new CyclicBarrier(5);
    private final Semaphore bicicletas = new Semaphore(15);
    private final Semaphore lugares = new Semaphore(15);

    private final Semaphore llenarBolsos = new Semaphore(1);
    private final Semaphore vaciarBolsos = new Semaphore(0);
    private final Semaphore llevarBolsos = new Semaphore(0);
    private final Semaphore traerBolsos = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);

    private final boolean[] bolsos = new boolean[15];

    private final VistaParque vp = new VistaParque();

    private void mensaje(String mensaje) {
        System.out.println(mensaje);
        vp.mostrar(mensaje);
    }

    /**
     * Abre el parque.
     */
    public synchronized void abrir() {
        abierto = true;
        actividadesAbiertas = true;
        mensaje("<<PARQUE ABIERTO>>");
    }

    /**
     * Cierra las actividades del parque.
     */
    public synchronized void cerrarActividades() {
        actividadesAbiertas = false;
        mensaje("<<ACTIVIDADES CERRADAS>>");
    }

    /**
     * Cierra el parque.
     */
    public synchronized void cerrar() {
        abierto = false;
        mensaje("<<PARQUE CERRADO>>");
    }

    public synchronized boolean estaAbierto() {
        return abierto;
    }


    /* Carrera de gomones por el río */

    /**
     * Ir al inicio de la carrera de gomones.
     * Si se va en tren, debe esperarse a que esté lleno (15 visitantes).
     * Si se va en bicicleta, debe haber disponibles (15 disponibles).
     *
     * @param irEnTren indica si va en tren (verdadero) o en bicicleta (falso)
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public void irACarrerasDeGomones(boolean irEnTren) throws InterruptedException, BrokenBarrierException {
        if (irEnTren) {
            trenIda.await();
            vp.printCarrera(String.format("🚃 %s va a en tren", Thread.currentThread().getName()));
            Thread.sleep(500);
        } else {
            bicicletas.acquire();
            vp.printCarrera(String.format("🚲 %s va a en bici", Thread.currentThread().getName()));
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
        }

        vp.printCarrera(String.format("%s llega al inicio", Thread.currentThread().getName()));
        lugares.acquire();
    }

    public int dejarPertenencias() throws InterruptedException {
        int llave = -1, n = 0;

        mutex.acquire();

        while (llave < 0 && n < bolsos.length) {
            if (!bolsos[n])
                llave = n;
        }

        mutex.release();
        llevarBolsos.release();

        return llave;
    }

    public void iniciarCarreraDeGomones() throws InterruptedException, BrokenBarrierException {
        carrera.await();
        vp.printCarrera(String.format("🏁 %s inicia la carrera", Thread.currentThread().getName()));
        Thread.sleep(ThreadLocalRandom.current().nextInt(15, 20) * 100);
    }

    public void finalizarCarreraDeGomones() {
        vp.printCarrera(String.format("🏁 %s finaliza la carrera", Thread.currentThread().getName()));
    }

    public void tomarPertenencias(int llaveDeBolso) throws InterruptedException {
        mutex.acquire();
        bolsos[llaveDeBolso] = false;
        mutex.release();
    }

    public void volverDeCarrerasDeGomones(boolean irEnTren) throws InterruptedException, BrokenBarrierException {
        lugares.release();

        if (irEnTren) {
            trenVuelta.await();
            vp.printCarrera(String.format("🚃 %s vuelve en tren", Thread.currentThread().getName()));
            Thread.sleep(500);
        } else {
            vp.printCarrera(String.format("🚲 %s vuelve en bici", Thread.currentThread().getName()));
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
            bicicletas.release();
        }
    }

    public void llevarBolsosAlFinal() throws InterruptedException {
        llevarBolsos.acquire();
        vp.printCarrera(String.format("🚙 %s lleva los bolsos", Thread.currentThread().getName()));
        Thread.sleep(500);
        vaciarBolsos.release();
    }

    public void traerBolsosAlInicio() throws InterruptedException {
        traerBolsos.acquire();
        vp.printCarrera(String.format("🚙 %s trae los bolsos", Thread.currentThread().getName()));
        Thread.sleep(500);
        llenarBolsos.release();
    }

}
