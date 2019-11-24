package procon.tpof2019;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Carrera de gomones por el río.
 * Implementado con semáforos.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class CarreraGomones {

    /**
     * Capacidad del tren.
     */
    public static final int CAPACIDAD_TREN = 15;

    /**
     * Cantidad de bicicletas.
     */
    public static final int CANTIDAD_BICICLETAS = 10;

    /**
     * Cantidad de gomones simples.
     */
    public static final int CANTIDAD_GOMONES_SIMPLES = 5;

    /**
     * Cantidad de gomones dobles.
     */
    public static final int CANTIDAD_GOMONES_DOBLES = 5;

    /**
     * Mutex.
     */
    private final Semaphore mutex = new Semaphore(1);

    /**
     * Habilita el inicio de una carrera.
     */
    private Semaphore carrera = new Semaphore(0, true);

    /**
     * Indica que un visitante en el inicio se puede preparar para la carrera.
     */
    private final Semaphore prepararse = new Semaphore(1, true);

    /**
     * Cantidad de bicicletas disponibles.
     */
    private final Semaphore bicicletas = new Semaphore(10);

    /**
     * Semáforos para el control del tren.
     */
    private final Semaphore trenVa = new Semaphore(0, true);
    private final Semaphore trenVuelve = new Semaphore(0, true);
    private final Semaphore subirseAlTren = new Semaphore(1, true);
    private final Semaphore bajarseDelTren = new Semaphore(0, true);
    private final Semaphore trenLLeno = new Semaphore(0);
    private final Semaphore esperarVisitantes = new Semaphore(0);
    private final Semaphore traerVisitantes = new Semaphore(0);

    /**
     * Semáforos para el control de la camioneta.
     */
    private final Semaphore vaciarBolsos = new Semaphore(0);
    private final Semaphore llevarBolsos = new Semaphore(0);
    private final Semaphore traerBolsos = new Semaphore(0);

    /**
     * Indica la cantidad de visitantes esperando en el inicio de una carrera.
     */
    private int visitantesEnInicio = 0;

    /**
     * Indica la cantidad de bolsos ocupados para una carrera.
     */
    private int bolsosOcupados = 0;

    /**
     * Indica la cantidad de lugares disponibles en gomones simples (5 en total).
     */
    private int lugaresEnGomonesSimplesOcupados = 0;

    /**
     * Indica la cantidad de lugares disponibles en gomones dobles (5 * 2 en total).
     */
    private int lugaresEnGomonesDoblesOcupados = 0;

    /**
     * Indica la cantidad de gomones listos para poder empezar una carrera.
     */
    private int gomonesListos = 0;

    /**
     * Indica la cantidad de visitantes listos a competir (mayor o igual a la cantidad de gomones listos).
     */
    private int visitantesACompetir = 0;

    /**
     * Indica la cantidad de carreras completadas.
     */
    private int totalDeCarreras = 0;

    /**
     * Indica el gomón ganador de una carrera.
     */
    private int ganador = -1;

    /**
     * Indica la cantidad de visitantes esperando ir a la carrera en tren.
     */
    private int esperandoIrEnTren = 0;

    /**
     * Indica la cantidad de visitantes esperando volver de la carrera en tren.
     */
    private int esperandoVolverTren = 0;

    /**
     * Indica la cantidad subiendo al tren (en ida o vuelta).
     */
    private int visitantesSubiendoAlTren = 0;

    /**
     * Indica la cantidad de visitantes arriba del tren (en ida o vuelta).
     */
    private int visitantesEnElTren = 0;

    /**
     * Indica que el tren está listo para ir a la carrera.
     */
    private boolean trenSalio = false;

    /**
     * Indica que el tren está listo para volver de la carrera.
     */
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

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Vista del parque.
     */
    private final VistaParque vista = VistaParque.getInstancia();

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public CarreraGomones(Parque parque) {
        this.parque = parque;
    }

    public boolean irEnBicicleta() throws InterruptedException {
        boolean vaEnBicicleta = false;
        String visitante = null;

        // Esperar algunos minutos opcionalmente
        if (bicicletas.tryAcquire(Tiempo.entreMinutos(0, 10), TimeUnit.MILLISECONDS)) {
            vaEnBicicleta = true;
            visitante = Thread.currentThread().getName();
            vista.printCarreraGomones(String.format("🚲 %s va a en bici", visitante));
            vista.sacarBicicleta();

            Thread.sleep(Tiempo.entreMinutos(20, 30));

            mutex.acquire();
            visitantesEnInicio++;
            vista.printCarreraGomones(String.format("🚲 %s llega al inicio", visitante));
            mutex.release();
        }

        return vaEnBicicleta;
    }

    public void volverEnBicicleta() throws InterruptedException {
        vista.printCarreraGomones(String.format("🚲 %s vuelve en bici", Thread.currentThread().getName()));

        Thread.sleep(Tiempo.entreMinutos(20, 30));

        vista.agregarBicicleta();
        bicicletas.release();
    }

    private boolean entroPrimerVisitanteAlTren = false;
    private boolean trenVolvio = false;
    private int esperandoVolverEnTren = 0;
    private final Semaphore primerVisitante = new Semaphore(0);

    /**
     * Visitante va a la carrera en tren, si obtiene un lugar en menos de 30 minutos.
     *
     * @return verdadero si fue en tren, falso si espero no obtuvo un lugar
     * @throws InterruptedException
     */
    public boolean irEnTren() throws InterruptedException {
        boolean vaEnTren = false;
        String visitante = Thread.currentThread().getName();

        // Espera entre 0 y 10 minutos para ir en tren
        if (subirseAlTren.tryAcquire(Tiempo.entreMinutos(0, 10), TimeUnit.MILLISECONDS)) {
            Thread.sleep(Tiempo.enMinutos(3));
            mutex.acquire();

            // Asegurarse de que el tren aún no ha salido
            if (!trenSalio) {
                vaEnTren = true;
                esperandoIrEnTren++;
                visitantesEnElTren++;

                vista.printCarreraGomones(String.format("🚃 %s sube al tren (%d/15)", visitante, esperandoIrEnTren));
                vista.agregarPasajero();

                // Se sube primer visitante: el tren espera hasta 30 minutos por más visitantes para salir
                if (!entroPrimerVisitanteAlTren) {
                    entroPrimerVisitanteAlTren = true;
                    primerVisitante.release();
                }

                // Se suben más visitantes si aún hay lugar
                if (esperandoIrEnTren < CAPACIDAD_TREN) {
                    subirseAlTren.release();
                } else {
                    //trenSalio = true;
                    trenLLeno.release();
                }

                mutex.release();
                trenVa.acquire();

                vista.printCarreraGomones(String.format("🚃 %s va en tren", visitante));

                bajarseDelTren.acquire();Thread.sleep(Tiempo.enMinutos(3));
                mutex.acquire();
                visitantesEnElTren--;
                visitantesEnInicio++;

                // Se siguen bajando visitantes del tren
                if (visitantesEnElTren > 0)
                    bajarseDelTren.release();
                else
                    esperarVisitantes.release();

                vista.printCarreraGomones(String.format("🚃 %s baja del tren (%d/15)", visitante, visitantesEnElTren));
                vista.sacarPasajero();
            }

            mutex.release();
        }

        return vaEnTren;
    }

    private final Semaphore subirseAlTrenVuelta = new Semaphore(0, true);

    public void volverEnTren() throws InterruptedException {
        String visitante = Thread.currentThread().getName();

        subirseAlTrenVuelta.acquire();Thread.sleep(Tiempo.enMinutos(3));
        mutex.acquire();

        // Verificar si se puede subir al tren
        if (!trenVolvio) {
            esperandoVolverEnTren--;
            visitantesEnElTren++;

            vista.printCarreraGomones(String.format("🚃 %s sube al tren (%d/15)", visitante, esperandoVolverEnTren));
            vista.agregarPasajero();

            // Si todos los visitantes subieron, el tren sale
            if (esperandoVolverEnTren > 0) {
                subirseAlTrenVuelta.release();
            } else {
                trenVolvio = true;
                traerVisitantes.release();
            }

            mutex.release();
            trenVuelve.acquire();

            vista.printCarreraGomones(String.format("🚃 %s vuelve en tren", visitante));

            bajarseDelTren.acquire();Thread.sleep(Tiempo.enMinutos(3));
            mutex.acquire();
            visitantesEnElTren--;

            // Si se bajaron todos los visitantes, permitir nuevos viajes
            if (visitantesEnElTren > 0) {
                bajarseDelTren.release();
            } else {
                trenVolvio = false;
                trenSalio = false;
                entroPrimerVisitanteAlTren = false;
                subirseAlTren.release();
            }

            vista.printCarreraGomones(String.format("🚃 %s baja del tren (%d/15)", visitante, visitantesEnElTren));
            vista.sacarPasajero();
        }

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
        vista.printCarreraGomones(String.format("👜 %s ocupa un bolso", Thread.currentThread().getName()));

        mutex.release();

        return llave;
    }

    /**
     * Visitante se sube a un gomón simple o doble.
     *
     * @return el gomón al que se subió
     * @throws InterruptedException
     */
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
            vista.agregarGomonSimple();
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
            vista.agregarGomonSimple();
        }

        vista.printCarreraGomones(String.format("🚣 %s ocupa un gomón %s", visitante, gomon >= 5 ? "doble" : "simple"));

        // Si no hay 5 gomones listos para iniciar la carrera, dejar a otros prepararse
        if (gomonesListos == 5
                || (!parque.actividadesAbiertas() && parque.getVisitantes() >= 0
                        && esperandoIrEnTren == 0 && visitantesEnElTren == 0 && visitantesEnInicio == 0)) {
            gomonesListos = 0;
            totalDeCarreras++;
            llevarBolsos.release();
            carrera.release(visitantesACompetir);
            visitantesACompetir = 0;
        } else {
            prepararse.release();
        }

        mutex.release();

        return gomon;
    }

    /**
     * Inicia la carrera de gomones.
     *
     * @throws InterruptedException
     */
    public void iniciarCarrera() throws InterruptedException {
        String visitante = Thread.currentThread().getName();

        carrera.acquire();
        mutex.acquire();
        visitantesEnInicio--;
        vista.printCarreraGomones(String.format("🏁 %s inicia la carrera #%d", visitante, totalDeCarreras));
        mutex.release();

        Thread.sleep(ThreadLocalRandom.current().nextInt(15, 20) * 100);
    }

    /**
     * Finaliza la carrera.
     *
     * @param llaveDeBolso la llave del bolso asignado
     * @param gomon el gomón utilizado
     * @throws InterruptedException
     */
    public void finalizarCarrera(int llaveDeBolso, int gomon) throws InterruptedException {
        String visitante = Thread.currentThread().getName();

        mutex.acquire();

        // Finalizar la carrera mostrando el ganador
        if (ganador < 0) {
            ganador = gomon;
            vista.printCarreraGomones(String.format("🏁 <<%s gana la carrera #%d>>", gomones[ganador], totalDeCarreras));
        } else {
            vista.printCarreraGomones(String.format("🏁 %s finaliza la carrera #%d", visitante, totalDeCarreras));
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

        vista.sacarGomonSimple();

        // Desocupar bolso
        bolsos[llaveDeBolso] = false;
        bolsosOcupados--;

        // Desocupar bolsos si aun hay ocupados en la camioneta
        if (bolsosOcupados > 0) {
            vaciarBolsos.release();
        } else { // Sino llevar bolsos desocupados al inicio nuevamente por la camioneta
            vista.sacarGomonSimple();
            ganador = -1;
            traerBolsos.release();
        }

        mutex.release();
    }

    /**
     * Camioneta lleva los bolsos (y bicicletas) al final.
     *
     * @throws InterruptedException
     */
    public void llevarBolsosAlFinal() throws InterruptedException {
        llevarBolsos.acquire();
        vista.printCarreraGomones(String.format("🚙 %s lleva los bolsos y bicicletas al final", Thread.currentThread().getName()));
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
        vista.printCarreraGomones(String.format("🚙 %s trae los bolsos y gomones al inicio", Thread.currentThread().getName()));
        Thread.sleep(500);
        prepararse.release();
    }

    private final Semaphore largarCarrera = new Semaphore(0);

    public void largarCarrera() throws InterruptedException {
        if (largarCarrera.tryAcquire(Tiempo.enMinutos(15), TimeUnit.MILLISECONDS)) {

        }
    }

    /**
     * Tren lleva los visitantes al inicio de la carrera.
     *
     * Consideraciones: el tren espera a que al menos se suba un visitante. Cuando se subió el primero, espera media
     * hora por más visitantes. Si se llena antes, viaja al inicio de carrera, sino, viaja con los que estén a bordo.
     *
     * @throws InterruptedException
     */
    public void llevarVisitantes() throws InterruptedException {
        primerVisitante.acquire();

        // Ir al inicio de carrera en 30 minutos luego de subirse un visitante
        if (!trenLLeno.tryAcquire(Tiempo.enMinutos(30), TimeUnit.MILLISECONDS)) {
            mutex.acquire();
            trenSalio = true;
            trenLLeno.tryAcquire(); // Se puede llenar justo después de cumplir la espera y antes de adquirir mutex
            mutex.release();
        }

        mutex.acquire();
        esperandoIrEnTren = 0;
        esperandoVolverEnTren = visitantesEnElTren;
        trenVa.release(visitantesEnElTren);
        mutex.release();

        vista.printCarreraGomones(String.format("🚃 %s va al inicio de carrera", Thread.currentThread().getName()));
        Thread.sleep(Tiempo.enMinutos(15));
        vista.printCarreraGomones(String.format("🚃 %s llega inicio de carrera", Thread.currentThread().getName()));
        vista.ubicarTren(1);

        bajarseDelTren.release();
    }

    /**
     * Tren va al final de la carrera a esperar los visitantes.
     *
     * @throws InterruptedException
     */
    public void esperarVisitantes() throws InterruptedException {
        esperarVisitantes.acquire();
        Thread.sleep(Tiempo.enMinutos(10));
        vista.printCarreraGomones(String.format("🚃 %s espera visitantes en final de carrera", Thread.currentThread().getName()));
        vista.ubicarTren(2);
        subirseAlTrenVuelta.release();
    }

    /**
     * Tren lleva los visitantes de nuevo al parque.
     *
     * @throws InterruptedException
     */
    public void traerVisitantes() throws InterruptedException {
        traerVisitantes.acquire();
        mutex.acquire();
        trenVuelve.release(visitantesEnElTren);
        mutex.release();

        vista.printCarreraGomones(String.format("🚃 %s vuelve al parque", Thread.currentThread().getName()));
        Thread.sleep(Tiempo.enMinutos(15));
        vista.printCarreraGomones(String.format("🚃 %s llega al parque", Thread.currentThread().getName()));
        vista.ubicarTren(0);

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
