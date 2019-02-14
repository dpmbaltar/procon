package procon.tpf1.e2;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Almacen {

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore debeReponer = new Semaphore(0);
    private final Semaphore estacionMezcla = new Semaphore(0);
    private final Semaphore jarra = new Semaphore(0);
    private final Semaphore envaseJugo = new Semaphore(0);
    private final Semaphore paqueteLevadura = new Semaphore(0);
    private final Semaphore unidadFermentacion = new Semaphore(0);
    private final Semaphore vinoParaProbar = new Semaphore(0);
    private int estacionesMezcla;
    private int jarras;
    private UnidadFermentacion[] unidadesFermentacion;
    private int envasesJugo;
    private int paquetesLevadura;
    private boolean hayNuevosVinos = false;
    private int cantidadMiembros = 0;

    private final Lock cerrojo = new ReentrantLock();
    private final Condition hayVinoFabricado = cerrojo.newCondition();
    private final Condition vinoProbado = cerrojo.newCondition();

    private final ArrayDeque<Vino> vinosFabricados = new ArrayDeque<>();

    /**
     * Constructor con las especificaciónes del almacen.
     * 
     * @param cantidadMiembros     cantidad de miembros
     * @param estacionesMezcla     cantidad de estaciones de mezcla
     * @param unidadesFermentacion cantidad de unidades de fermentación
     * @param jarras               cantidad de jarras (10 litros c/u)
     * @param envasesJugo          cantidad de envases de jugo (5 litros c/u)
     * @param paquetesLevadura     cantidad de levadura (10 litros de vino c/u)
     */
    public Almacen(int cantidadMiembros, int estacionesMezcla,
            int unidadesFermentacion, int jarras, int envasesJugo,
            int paquetesLevadura) {
        this.cantidadMiembros = cantidadMiembros;
        this.estacionesMezcla = estacionesMezcla;
        this.unidadesFermentacion = new UnidadFermentacion[unidadesFermentacion];
        for (int i = 0; i < unidadesFermentacion; i++)
            this.unidadesFermentacion[i] = new UnidadFermentacion(i);
        this.jarras = jarras;
        this.envasesJugo = envasesJugo;
        this.paquetesLevadura = paquetesLevadura;
        estacionMezcla.release(estacionesMezcla);
        unidadFermentacion.release(unidadesFermentacion);
        jarra.release(jarras);
        envaseJugo.release(envasesJugo);
        paqueteLevadura.release(paquetesLevadura);
    }

    public void reponer(int envasesJugo, int paquetesLevadura)
            throws InterruptedException {
        debeReponer.acquire();
        // mutex.acquire();
        // if (this.envasesJugo < 2 || this.paquetesLevadura < 1) {
        // this.envasesJugo += envasesJugo;
        // this.paquetesLevadura += paquetesLevadura;
        envaseJugo.release(envasesJugo);
        paqueteLevadura.release(paquetesLevadura);
        // }
        System.out.println(
                Thread.currentThread().getName() + ">>> repone ingredientes");
        // mutex.release();
    }

    public void entrar(Miembro miembro) throws InterruptedException {

    }

    /**
     * Simula iniciar la mezcla de un miembro, si hay una estación disponible.
     * Si no hay estación disponible, entonces espera que a uno termine para
     * probar su vino y luego seguir esperando por una estación.
     * 
     * @return verdadero si inición mezcla, falso en caso contrario
     * @throws InterruptedException
     */
    public boolean iniciarMezcla() throws InterruptedException {
        boolean inicioMezcla = false;
        if (estacionMezcla.tryAcquire()) {
            jarra.acquire(2);
            if (!envaseJugo.tryAcquire(2)) {
                debeReponer.release();
                envaseJugo.acquire(2);
            }
            if (!paqueteLevadura.tryAcquire()) {
                debeReponer.release();
                paqueteLevadura.acquire();
            }
            System.out.println(
                    Thread.currentThread().getName() + ">>> inicia mezcla");
            inicioMezcla = true;
        }

        return inicioMezcla;
    }

    /**
     * Simula finalizar la mezcla de un miembro. Liber una estación.
     * 
     * @return
     * @throws InterruptedException
     */
    public int finalizarMezcla() throws InterruptedException {
        System.out.println(
                Thread.currentThread().getName() + ">>> finaliza mezcla");

        // System.out.println(estacionMezcla);
        // System.out.println(jarra);
        // System.out.println(unidadFermentacion);
        estacionMezcla.release();

        return 10;
    }

    public UnidadFermentacion adquirirUnidadFermentacion(Miembro miembro)
            throws InterruptedException {
        UnidadFermentacion unidadAdquirida = null;

        if (unidadFermentacion.tryAcquire()) {
            mutex.acquire();
            for (int i = 0; i < unidadesFermentacion.length; i++) {
                if (!unidadesFermentacion[i].estaOcupada()) {
                    unidadAdquirida = unidadesFermentacion[i];
                    unidadAdquirida.ocupar(miembro);
                    break;
                }
            }

            jarra.release();
            System.out.println(Thread.currentThread().getName()
                    + ">>> adquiere una unidad de fermentación");
            mutex.release();
        }

        return unidadAdquirida;
    }

    public void liberarUnidadFermentacion(UnidadFermentacion uf)
            throws InterruptedException {
        mutex.acquire();
        Vino vino = uf.getVino();
        uf.desocupar();
        unidadFermentacion.release();
        jarra.release();
        
        cerrojo.lock();
        try {
            vinosFabricados.add(vino);
            vinoProbado.signalAll();
        } finally {
            cerrojo.unlock();
        }
        mutex.release();
    }

    public boolean iniciarFermentacion() throws InterruptedException {
        boolean inicioFermentacion = false;
        unidadFermentacion.acquire();
        System.out.println(
                Thread.currentThread().getName() + ">>> inicia fermentación");
        jarra.release();

        return inicioFermentacion;
    }

    public void finalizarFermentacion() throws InterruptedException {
        // jarra.acquire();
        System.out.println(
                Thread.currentThread().getName() + ">>> finaliza fermentación");
        unidadFermentacion.release();
    }

    public void salir() {

    }

    public boolean esperarPruebaVino(Vino vino) throws InterruptedException {
        boolean probaronVino = false;
        cerrojo.lock();
        try {
            // Primero, el fabricante prueba su propio vino
            Miembro fabricante = vino.getFabricante();
            vino.probar(fabricante);

            // Esperar que el resto de los miembros prueben el vino
            while (vino.getCantidadProbaron() < cantidadMiembros
                    || vinosFabricados.getLast().estaProbadoPor(fabricante))
                vinoProbado.await();

            // Verificar el motivo por el cual se "despertó" este miembro
            if (vino.getCantidadProbaron() >= cantidadMiembros) {
                // jarra.release();
                probaronVino = true;
                System.out.println(fabricante.getNombre() + ">>> vino probado");
            }
        } catch (NoSuchElementException e) {
            //e.printStackTrace();
        } finally {
            cerrojo.unlock();
        }

        return probaronVino;
    }

    /**
     * Simula probar un vino por un miembro. Si hay vinos fabricados los prueba,
     * sino espera hasta que: (1) le avisen que hay vino para probar; (2) pasa
     * una cierta cantidad de tiempo (aproximadamente lo que tarda la
     * fermentación de un vino). De esta forma, si el miembro que va a probar
     * algún vino tenía su propio vino fermentando, de no haber otro vino
     * fabricado para probar transcurrido este tiempo, entonces sale de la
     * espera y continúa su etapa de fabricación en vez de quedarse "dormido"
     * hasta que otro miembro termine un vino.
     * 
     * @param miembro el miembro que prueba vinos
     * @throws InterruptedException
     */
    public int probarVinos(Miembro miembro) throws InterruptedException {
        int vinosProbados = 0;

        cerrojo.lock();
        try {
            // No esperar en bucle para seguir su proceso si aún no hay vinos
            if (vinosFabricados.isEmpty())
                hayVinoFabricado.await(2, TimeUnit.SECONDS);

            // Si aún no hay vinos, salir de la espera y seguir su proceso
            if (!vinosFabricados.isEmpty()) {
                Vino vino;
                Iterator<Vino> iterador = vinosFabricados.iterator();
                while (iterador.hasNext()) {
                    vino = iterador.next();
                    if (vino.probar(miembro)) {
                        vinosProbados++;
                        System.out.println(
                                miembro.getNombre() + ">>> prueba vino de "
                                        + vino.getFabricante().getNombre());
                    }

                    if (vino.getCantidadProbaron() >= cantidadMiembros) {
                        iterador.remove();
                        vinoProbado.signalAll();
                    }
                }
            }
        } finally {
            cerrojo.unlock();
        }

        return vinosProbados;
    }

}
