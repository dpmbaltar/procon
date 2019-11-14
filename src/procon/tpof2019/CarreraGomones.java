package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Carrera de gomones por el río.
 * Implementado con semáforos.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class CarreraGomones {

    private CyclicBarrier trenIda = new CyclicBarrier(15);
    private CyclicBarrier trenVuelta = new CyclicBarrier(15);
    private CountDownLatch carrera = new CountDownLatch(5);

    private final Semaphore bicicletas = new Semaphore(15);
    private final Semaphore vaciarBolsos = new Semaphore(0);
    private final Semaphore llevarBolsos = new Semaphore(0);
    private final Semaphore traerBolsos = new Semaphore(0);
    private final Semaphore prepararse = new Semaphore(1, true);
    private final Semaphore mutex = new Semaphore(1);

    private int visitantesEnInicio = 0;
    private int ganador = -1;
    private int totalDeCarreras = 0;
    private int lugaresEnGomonesSimplesOcupados = 0;
    private int lugaresEnGomonesDoblesOcupados = 0;
    private int gomonesListos = 0;
    private int bolsosOcupados = 0;
    private boolean carreraSuspendida = false;

    /**
     * Los bolsos para las pertenencias (10).
     * La cantidad total se debe a que 10 es el máximo de visitantes por carrera, y sucede cuando una carrera inicia
     * con 5 gomones dobles. En el resto de los casos se utilizan como mínimo 5 bolsos.
     */
    private final boolean[] bolsos = new boolean[10];

    /**
     * Los gomones disponibles: 5 simples y 5 dobles.
     * Índices entre 0 y 4 son simples.
     * Índices entre 5 y 9 son dobles.
     */
    private final String[] gomones = new String[10];

    private final VistaParque vista = VistaParque.getInstance();

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
            vista.printCarrera(String.format("🚃 %s va a en tren", Thread.currentThread().getName()));
            Thread.sleep(500);
        } else {
            bicicletas.acquire();
            vista.printCarrera(String.format("🚲 %s va a en bici", Thread.currentThread().getName()));
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
        }

        mutex.acquire();
        visitantesEnInicio++;
        vista.printCarrera(String.format("🏁 %s llega al inicio", Thread.currentThread().getName()));
        mutex.release();
    }

    /**
     * Comienza a prepararse para la carrera: deja sus pertenencias en un bolso y obtiene su llave.
     *
     * @return la llave del bolso
     * @throws InterruptedException
     */
    public int dejarPertenencias() throws InterruptedException {
        int llave = -1;

        prepararse.acquire();
        mutex.acquire();

        // Utilizar un bolso
        bolsos[bolsosOcupados] = true;
        llave = bolsosOcupados;
        bolsosOcupados++;
        vista.printCarrera(String.format("👜 %s ocupa un bolso", Thread.currentThread().getName()));

        mutex.release();

        return llave;
    }

    public int subirseAGomon() throws InterruptedException {
        int gomon = -1;
        String visitante = Thread.currentThread().getName();

        mutex.acquire();

        // Elegir gomón doble si ya hay alguien esperando en uno, sino elegir aleatoriamente simple o doble
        if ((lugaresEnGomonesDoblesOcupados % 2) != 0) {
            // En este caso, "lugaresEnGomonesDoblesOcupados" es siempre 1, 3, 5, 7 o 9
            gomon = 5 + (lugaresEnGomonesDoblesOcupados / 2);
            gomones[gomon] += " y " + visitante;
            lugaresEnGomonesDoblesOcupados++;
            gomonesListos++;
            carrera.countDown();
        } else if (/*actividadesAbiertas() || */ThreadLocalRandom.current().nextBoolean()) {
            gomon = lugaresEnGomonesSimplesOcupados;
            gomones[gomon] = visitante;
            lugaresEnGomonesSimplesOcupados++;
            gomonesListos++;
            carrera.countDown();
        } else { // En este caso, "lugaresEnGomonesDoblesOcupados" es siempre 0, 2, 4, u 8
            gomon = 5 + (lugaresEnGomonesDoblesOcupados / 2);
            gomones[gomon] = visitante;
            lugaresEnGomonesDoblesOcupados++;
        }

        vista.printCarrera(String.format("🚣 %s ocupa un gomón", visitante));
        vista.agregarGomon();

        // Si no hay 5 gomones listos para iniciar la carrera, dejar a otros prepararse
        if (gomonesListos < 5) {

            //FIXME: Cancelar carrera cuando corresponda
            /*if (!actividadesAbiertas() && !carreraSuspendida && (gomonesListos + visitantesEnInicio) < 5) {
                vp.printCarrera("🏁 <<carreras suspendidas por cierre>>");
                System.out.println("en inicio: " + visitantesEnInicio);
                carreraSuspendida = true;
                for (int i = 0; i < 5; i++)
                    carrera.countDown();
            }*/

            prepararse.release();
        } else { // Si hay 5 gomones listos, indicar a la camioneta que lleve los bolsos ya que iniciará la carrera
            gomonesListos = 0;
            totalDeCarreras++;
            llevarBolsos.release();
        }

        mutex.release();

        return gomon;
    }

    public void iniciarCarreraDeGomones() throws InterruptedException, BrokenBarrierException {
        System.out.println(Thread.currentThread().getName() + " antes del latch " + carrera.getCount());
        carrera.await();
        System.out.println(Thread.currentThread().getName() + " paso el latch");
        mutex.acquire();
        visitantesEnInicio--;
        vista.printCarrera(String.format("🏁 %s inicia la carrera #%d", Thread.currentThread().getName(), totalDeCarreras));
        mutex.release();
        System.out.println(Thread.currentThread().getName() + " paso el mutex");
        Thread.sleep(ThreadLocalRandom.current().nextInt(15, 20) * 100);
    }

    public void finalizarCarreraDeGomones(int llaveDeBolso, int gomon) throws InterruptedException {
        String visitante = Thread.currentThread().getName();

        mutex.acquire();

        // Finalizar la carrera mostrando el ganador
        if (ganador < 0) {
            ganador = gomon;
            vista.printCarrera(String.format("🏁 <<%s finaliza primero la carrera #%d>>", gomones[ganador], totalDeCarreras));
        } else {
            vista.printCarrera(String.format("🏁 %s finaliza la carrera #%d", visitante, totalDeCarreras));
        }

        mutex.release();
        vaciarBolsos.acquire(); // La camioneta debe haber llegado para vaciar desocupar los bolsos
        mutex.acquire();

        // Liberar gomon (simple o doble, según corresponda)
        gomones[gomon] = null;
        if (gomon < 5)
            lugaresEnGomonesSimplesOcupados--;
        else
            lugaresEnGomonesDoblesOcupados--;

        // Desocupar bolso
        bolsos[llaveDeBolso] = false;
        bolsosOcupados--;

        // Desocupar bolsos si aun hay ocupados en la camioneta
        if (bolsosOcupados > 0) {
            vaciarBolsos.release();
        } else { // Sino llevar bolsos desocupados al inicio nuevamente por la camioneta
            vista.sacarGomones();
            ganador = -1;
            carrera = new CountDownLatch(5);
            traerBolsos.release();
        }

        mutex.release();
    }

    public void volverDeCarrerasDeGomones(boolean irEnTren) throws InterruptedException, BrokenBarrierException {
        if (irEnTren) {
            trenVuelta.await();
            vista.printCarrera(String.format("🚃 %s vuelve en tren", Thread.currentThread().getName()));
            Thread.sleep(500);
        } else {
            vista.printCarrera(String.format("🚲 %s vuelve en bici", Thread.currentThread().getName()));
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
            bicicletas.release();
        }
    }

    public void llevarBolsosAlFinal() throws InterruptedException {
        llevarBolsos.acquire();
        vista.printCarrera(String.format("🚙 %s lleva los bolsos al final", Thread.currentThread().getName()));
        Thread.sleep(500);
        vaciarBolsos.release();
    }

    public void traerBolsosAlInicio() throws InterruptedException {
        traerBolsos.acquire();
        vista.printCarrera(String.format("🚙 %s trae los bolsos al inicio", Thread.currentThread().getName()));
        Thread.sleep(500);
        prepararse.release();
    }

    public synchronized boolean hayVisitantesEnInicio() throws InterruptedException {
        boolean hayVisitantes = false;
        mutex.acquire();
        hayVisitantes = visitantesEnInicio > 0;
        mutex.release();

        return hayVisitantes;
    }

}
