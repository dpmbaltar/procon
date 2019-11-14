package procon.tpof2019;

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

    private CyclicBarrier trenVuelta = new CyclicBarrier(15);
    private Semaphore carrera1 = new Semaphore(0, true);

    private final Semaphore trenVa = new Semaphore(1);
    private final Semaphore trenVuelve = new Semaphore(0);
    private final Semaphore subirseAlTren = new Semaphore(0, true);
    private final Semaphore bajarseDelTren = new Semaphore(0);
    private final Semaphore bicicletas = new Semaphore(10);

    private final Semaphore llevarVisitantes = new Semaphore(0);
    private final Semaphore esperarVisitantes = new Semaphore(0);
    private final Semaphore traerVisitantes = new Semaphore(0);

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

    private int visitantesACompetir = 0;

    private int esperandoIrEnTren = 0;
    private int esperandoVolverTren = 0;
    private int visitantesSubiendoAlTren = 0;
    private int visitantesEnElTren = 0;
    private boolean trenListoIr = false;
    private boolean trenListoVolver = false;

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
     */
    public void ir(boolean irEnTren) throws InterruptedException {
        String visitante;

        mutex.acquire();
        visitante = Thread.currentThread().getName();

        if (irEnTren) {
            esperandoIrEnTren++;

            // Si el tren está listo, llevar visitantes a la carrera
            if (esperandoIrEnTren == 15 && !trenListoIr) {
                esperandoIrEnTren -= 15;
                visitantesSubiendoAlTren = 15;
                trenListoIr = true;
                subirseAlTren.release();
            }

            mutex.release();
            subirseAlTren.acquire();
            vista.printCarrera(String.format("🚃 %s va en tren (se sube)", visitante));
            mutex.acquire();
            visitantesSubiendoAlTren--;
            visitantesEnElTren++;

            // Dejar a otros esperando subirse
            if (visitantesSubiendoAlTren > 0)
                subirseAlTren.release();
            else
                llevarVisitantes.release();

            mutex.release();
            bajarseDelTren.acquire();
            vista.printCarrera(String.format("🚃 %s va en tren (se baja)", visitante));
            mutex.acquire();
            visitantesEnElTren--;

            // Dejar a otros bajarse
            if (visitantesEnElTren > 0)
                bajarseDelTren.release();
            else
                esperarVisitantes.release();

            mutex.release();
        } else {
            mutex.release();
            bicicletas.acquire();
            vista.printCarrera(String.format("🚲 %s va a en bici", visitante));
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
        }

        mutex.acquire();
        visitantesEnInicio++;
        vista.printCarrera(String.format("🏁 %s llega al inicio", visitante));
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
            visitantesACompetir += 2;
            vista.agregarGomon();
        } else if ((visitantesEnInicio - visitantesACompetir) > 0 && false/*ThreadLocalRandom.current().nextBoolean()*/) {
            gomon = 5 + (lugaresEnGomonesDoblesOcupados / 2);
            gomones[gomon] = visitante;
            lugaresEnGomonesDoblesOcupados++;
        } else {
            gomon = lugaresEnGomonesSimplesOcupados;
            gomones[gomon] = visitante;
            lugaresEnGomonesSimplesOcupados++;
            gomonesListos++;
            visitantesACompetir++;
            vista.agregarGomon();
        }

        vista.printCarrera(String.format("🚣 %s ocupa un gomón %s", visitante, gomon >= 5 ? "doble" : "simple"));

        // Si no hay 5 gomones listos para iniciar la carrera, dejar a otros prepararse
        if (gomonesListos < 5) {
            prepararse.release();
        } else { // Si hay 5 gomones listos, indicar a la camioneta que lleve los bolsos ya que iniciará la carrera
            gomonesListos = 0;
            totalDeCarreras++;
            llevarBolsos.release();
            carrera1.release(visitantesACompetir);
            visitantesACompetir = 0;
        }

        System.out.println(String.format("gomones listos: %d", gomonesListos));
        System.out.println(String.format("incio: %d competir: %d", visitantesEnInicio, visitantesACompetir));
        System.out.println(String.format("gom simples: %d gom dobles: %d", lugaresEnGomonesSimplesOcupados, lugaresEnGomonesDoblesOcupados));
        System.out.println(String.format("bolsos: %d", bolsosOcupados));

        mutex.release();

        return gomon;
    }

    /**
     * Inicia la carrera de gomones.
     *
     * @throws InterruptedException
     */
    public void iniciarCarrera() throws InterruptedException {
        carrera1.acquire();
        mutex.acquire();
        visitantesEnInicio--;
        vista.printCarrera(String.format("🏁 %s inicia la carrera #%d", Thread.currentThread().getName(), totalDeCarreras));
        mutex.release();

        Thread.sleep(ThreadLocalRandom.current().nextInt(15, 20) * 100);
    }

    /**
     * Finaliza la carrera.
     *
     * @param llaveDeBolso
     * @param gomon
     * @throws InterruptedException
     */
    public void finalizarCarrera(int llaveDeBolso, int gomon) throws InterruptedException {
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
            traerBolsos.release();
        }

        mutex.release();
    }

    /**
     * Volver al parque.
     *
     * @param irEnTren verdadero si vuelve en tren, falso si en bicicleta
     * @throws InterruptedException
     */
    public void volver(boolean irEnTren) throws InterruptedException {
        String visitante;

        mutex.acquire();
        visitante = Thread.currentThread().getName();

        if (irEnTren) {
            esperandoVolverTren++;

            // Si el tren está listo, llevar visitantes a la carrera
            if (esperandoVolverTren == 15 && !trenListoVolver) {
                esperandoVolverTren -= 15;
                visitantesSubiendoAlTren = 15;
                trenListoVolver = true;
                trenVuelve.release();
            }

            mutex.release();
            trenVuelve.acquire();
            vista.printCarrera(String.format("🚃 %s vuelve en tren (se sube)", visitante));
            mutex.acquire();
            visitantesSubiendoAlTren--;
            visitantesEnElTren++;

            // Dejar a otros esperando subirse
            if (visitantesSubiendoAlTren > 0)
                trenVuelve.release();
            else
                traerVisitantes.release();

            mutex.release();
            bajarseDelTren.acquire();
            vista.printCarrera(String.format("🚃 %s vuelve en tren (se baja)", visitante));
            mutex.acquire();
            visitantesEnElTren--;

            // Dejar a otros bajarse
            if (visitantesEnElTren > 0)
                bajarseDelTren.release();
            else {
                trenListoVolver = false;
                trenListoIr = false;

                // Si hay esperando para venir en tren, habilitarlo
                if (esperandoIrEnTren >= 15 && !trenListoIr) {
                    esperandoIrEnTren -= 15;
                    visitantesSubiendoAlTren = 15;
                    trenListoIr = true;
                    subirseAlTren.release();
                }
            }

            mutex.release();
        } else {
            mutex.release();
            vista.printCarrera(String.format("🚲 %s vuelve en bici", Thread.currentThread().getName()));
            Thread.sleep(ThreadLocalRandom.current().nextInt(8, 10) * 100);
            bicicletas.release();
        }

        vista.printCarrera(String.format("%s llega al parque", visitante));
    }

    /**
     * Camioneta lleva los bolsos (y bicicletas) al final.
     *
     * @throws InterruptedException
     */
    public void llevarBolsosAlFinal() throws InterruptedException {
        llevarBolsos.acquire();
        vista.printCarrera(String.format("🚙 %s lleva los bolsos y bicicletas al final", Thread.currentThread().getName()));
        Thread.sleep(500);
        vaciarBolsos.release();
    }

    /**
     * Camioneta trae los bolsos y gomones al inicio de la carrera.
     *
     * @throws InterruptedException
     */
    public void traerBolsosAlInicio() throws InterruptedException {
        traerBolsos.acquire();
        vista.printCarrera(String.format("🚙 %s trae los bolsos y gomones al inicio", Thread.currentThread().getName()));
        Thread.sleep(500);
        prepararse.release();
    }

    /**
     * Tren lleva los visitantes al inicio de la carrera.
     *
     * @throws InterruptedException
     */
    public void llevarVisitantes() throws InterruptedException {
        llevarVisitantes.acquire();
        vista.printCarrera(String.format("🚃 %s lleva visitantes al inicio de carrera", Thread.currentThread().getName()));
        Thread.sleep(500);
        bajarseDelTren.release();
    }

    /**
     * Tren va al final de la carrera a esperar los visitantes.
     *
     * @throws InterruptedException
     */
    public void esperarVisitantes() throws InterruptedException {
        esperarVisitantes.acquire();
        vista.printCarrera(String.format("🚃 %s espera visitantes en final de carrera", Thread.currentThread().getName()));
    }

    /**
     * Tren lleva los visitantes de nuevo al parque.
     *
     * @throws InterruptedException
     */
    public void traerVisitantes() throws InterruptedException {
        traerVisitantes.acquire();
        vista.printCarrera(String.format("🚃 %s trae visitantes al parque", Thread.currentThread().getName()));
        Thread.sleep(500);
        bajarseDelTren.release();
    }

    /**
     * Devuelve verdadero si hay visitntes en el inicio de la carrera.
     *
     * @return verdadero si hay visitantes, falso en caso contrario
     * @throws InterruptedException
     */
    public boolean hayVisitantesEnInicio() throws InterruptedException {
        boolean hayVisitantes = false;

        mutex.acquire();
        hayVisitantes = visitantesEnInicio > 0;
        mutex.release();

        return hayVisitantes;
    }

}
