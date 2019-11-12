package procon.tpof2019;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
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

    public synchronized boolean actividadesAbiertas() {
        return actividadesAbiertas;
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

        mutex.acquire();
        visitantesEnInicio++;
        vp.printCarrera(String.format("🏁 %s llega al inicio", Thread.currentThread().getName()));
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
        vp.printCarrera(String.format("👜 %s ocupa un bolso", Thread.currentThread().getName()));

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
        } else if (actividadesAbiertas() || ThreadLocalRandom.current().nextBoolean()) {
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

        vp.printCarrera(String.format("🚣 %s ocupa un gomón", visitante));
        vp.agregarGomon();

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
        vp.printCarrera(String.format("🏁 %s inicia la carrera #%d", Thread.currentThread().getName(), totalDeCarreras));
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
            vp.printCarrera(String.format("🏁 <<%s finaliza primero la carrera #%d>>", gomones[ganador], totalDeCarreras));
        } else {
            vp.printCarrera(String.format("🏁 %s finaliza la carrera #%d", visitante, totalDeCarreras));
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
            vp.sacarGomones();
            ganador = -1;
            carrera = new CountDownLatch(5);
            traerBolsos.release();
        }

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
        vp.printCarrera(String.format("🚙 %s lleva los bolsos al final", Thread.currentThread().getName()));
        Thread.sleep(500);
        vaciarBolsos.release();
    }

    public void traerBolsosAlInicio() throws InterruptedException {
        traerBolsos.acquire();
        vp.printCarrera(String.format("🚙 %s trae los bolsos al inicio", Thread.currentThread().getName()));
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

    /* Faro-Mirador con vista a 40 m de altura y descenso en tobogán */

    private final BlockingQueue<Integer> descenso = new SynchronousQueue<>(true);
    private final BlockingQueue<String> tobogan1 = new ArrayBlockingQueue<>(1, true);
    private final BlockingQueue<String> tobogan2 = new ArrayBlockingQueue<>(1, true);
    private final BlockingQueue<String> escaleraFaroMirador = new ArrayBlockingQueue<>(10, true);
    private final BlockingQueue<String> colaFaroMirador = new ArrayBlockingQueue<>(1, true);
    private final Object monitorEcaleraFaroMirador = new Object();
    private int cantidadDescensos = 0;

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
    public void irAFaroMirador() throws InterruptedException {
        String visitante = Thread.currentThread().getName();
        vp.printFaroMirador(String.format("%s va al Faro-Mirador", visitante));
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
        vp.printFaroMirador(String.format("%s inicia ascenso", visitante));
        colaFaroMirador.poll();
        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
    }

    /**
     * Finaliza ascenso al faro por escalera y observa desde lo alto del Faro-Mirador.
     *
     * @throws InterruptedException
     */
    public void finalizarAscensoPorEscalera() throws InterruptedException {
        synchronized (monitorEcaleraFaroMirador) {
            String visitante = Thread.currentThread().getName();

            // Respetar orden de ascenso
            while (!visitante.contentEquals(escaleraFaroMirador.peek()))
                monitorEcaleraFaroMirador.wait();

            vp.printFaroMirador(String.format("%s termina ascenso. Observa desde lo alto...", visitante));
            escaleraFaroMirador.poll();
            monitorEcaleraFaroMirador.notifyAll();
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

        vp.printFaroMirador(String.format("%s es asignado el tobogan %d", visitante, tobogan));

        // Utilizar el tobogán asignado, cuando esté disponible
        if (tobogan == 0)
            tobogan1.put(visitante);
        else if (tobogan == 1)
            tobogan2.put(visitante);

        vp.printFaroMirador(String.format("%s inicia descenso en el tobogan %d", visitante, tobogan));
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
        vp.printFaroMirador(String.format("%s termina descenso en el tobogan %d", visitante, tobogan));

        // Liberar el tobogán utilizado
        if (tobogan == 0)
            visitante = tobogan1.take();
        else if (tobogan == 1)
            visitante = tobogan2.take();

        vp.printFaroMirador(String.format("%s vuelve del Faro-Mirador", visitante));
    }

}
