package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
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
    //private CyclicBarrier carrera = new CyclicBarrier(5);
    private CountDownLatch carrera = new CountDownLatch(5);

    private final Semaphore bicicletas = new Semaphore(15);
    private final Semaphore lugares = new Semaphore(15);
    private final Semaphore llenarBolsos = new Semaphore(1);
    private final Semaphore vaciarBolsos = new Semaphore(0);
    private final Semaphore llevarBolsos = new Semaphore(0);
    private final Semaphore traerBolsos = new Semaphore(0);
    private final Semaphore prepararse = new Semaphore(1, true);
    private final Semaphore mutex = new Semaphore(1);

    private String ganador = null;
    private int totalDeCarreras = 0;
    private int lugaresEnGomonesSimplesOcupados = 0;
    private int lugaresEnGomonesDoblesOcupados = 0;
    private int gomonesListos = 0;
    private int bolsosOcupados = 0;

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
    private final boolean[] gomones = new boolean[10];

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

        vp.printCarrera(String.format("🏁 %s llega al inicio", Thread.currentThread().getName()));
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

        mutex.release();

        return llave;
    }

    public int prepararseParaLaCarrera() throws InterruptedException {
        int gomon = -1;

        mutex.acquire();

        // Elegir gomón doble si ya hay alguien esperando en uno, sino elegir aleatoriamente simple o doble
        if ((lugaresEnGomonesDoblesOcupados % 2) != 0) {
            // En este caso, "lugaresEnGomonesDoblesOcupados" es siempre 1, 3, 5, 7 o 9;
            gomones[5 + (lugaresEnGomonesDoblesOcupados / 2)] = true;
            gomon = lugaresEnGomonesDoblesOcupados;
            lugaresEnGomonesDoblesOcupados++;
            gomonesListos++;
        } else if (ThreadLocalRandom.current().nextBoolean()) {
            gomones[lugaresEnGomonesSimplesOcupados] = true;
            gomon = lugaresEnGomonesSimplesOcupados;
            lugaresEnGomonesSimplesOcupados++;
            gomonesListos++;
        } else {
            gomon = lugaresEnGomonesDoblesOcupados;
            lugaresEnGomonesDoblesOcupados++;
        }

        // Si no hay 5 gomones listos para iniciar la carrera, dejar a otros prepararse
        if (gomonesListos < 5) {
            prepararse.release();
        } else { // Si hay 5 gomones listos, indicar a la camioneta que lleve los bolsos ya que iniciará la carrera
            llevarBolsos.release();
            totalDeCarreras++;
        }

        mutex.release();

        return gomon;
    }

    public void iniciarCarreraDeGomones() throws InterruptedException, BrokenBarrierException {
        carrera.countDown();
        carrera.await();
        mutex.acquire();
        vp.printCarrera(String.format("🏁 %s inicia la carrera #%d", Thread.currentThread().getName(), totalDeCarreras));
        mutex.release();
        Thread.sleep(ThreadLocalRandom.current().nextInt(15, 20) * 100);
    }

    public void finalizarCarreraDeGomones(int llaveDeBolso, int gomon) throws InterruptedException {
        String visitante = Thread.currentThread().getName();

        mutex.acquire();

        if (ganador == null) {
            ganador = visitante;
            vp.printCarrera(String.format("🏁 ¡¡¡%s gana la carrera #%d!!!", visitante, totalDeCarreras));
        } else {
            vp.printCarrera(String.format("🏁 %s finaliza la carrera #%d", visitante, totalDeCarreras));
        }

        mutex.release();
        vaciarBolsos.acquire(); // La camioneta debe haber llegado para vaciar desocupar los bolsos
        mutex.acquire();

        // Liberar gomon (simple o doble, según corresponda)
        gomones[gomon] = false;
        if (gomon < 5)
            lugaresEnGomonesSimplesOcupados--;
        else
            lugaresEnGomonesDoblesOcupados--;

        // Desocupar bolso
        bolsos[llaveDeBolso] = false;
        bolsosOcupados--;

        // Desocupar bolsos si aun hay ocupados en la camioneta
        if (bolsosOcupados > 0)
            vaciarBolsos.release();
        else // Sino llevar bolsos desocupados al inicio nuevamente por la camioneta
            traerBolsos.release();

        mutex.release();
    }

    public void volverDeCarrerasDeGomones(boolean irEnTren) throws InterruptedException, BrokenBarrierException {
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
        prepararse.release();
    }

}
