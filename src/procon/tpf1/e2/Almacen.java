package procon.tpf1.e2;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * El almacén del club.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Almacen {

    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore debeReponer = new Semaphore(0);
    private final Semaphore estacionMezcla = new Semaphore(0);
    private final Semaphore jarra = new Semaphore(0);
    private final Semaphore envaseJugo = new Semaphore(0);
    private final Semaphore paqueteLevadura = new Semaphore(0);
    private final Semaphore unidadFermentacion = new Semaphore(0);
    private int estacionesMezcla;
    private int jarras;
    private UnidadFermentacion[] unidadesFermentacion;
    private int envasesJugo;
    private int paquetesLevadura;
    private int cantidadVinosFabricados = 0;
    private int cantidadMiembros = 0;

    /**
     * Los vinos fabricados a la espera de ser probado por todos los miembros.
     */
    private final ArrayDeque<Vino> vinosParaProbar = new ArrayDeque<>();

    private final Lock cerrojo = new ReentrantLock();
    private final Condition hayVinoFabricado = cerrojo.newCondition();
    private final Condition vinoProbado = cerrojo.newCondition();

    private final Condition reponerIngredientes = cerrojo.newCondition();
    private final Condition hayIngredientes = cerrojo.newCondition();
    private final Condition estacionMezclaLibre = cerrojo.newCondition();

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
//        estacionMezcla.release(estacionesMezcla);
//        unidadFermentacion.release(unidadesFermentacion);
//        jarra.release(jarras);
//        envaseJugo.release(envasesJugo);
//        paqueteLevadura.release(paquetesLevadura);
    }

    /**
     * Devuelve la cantidad de miembros.
     * 
     * @return la cantidad de miembros
     */
    public synchronized int getCantidadMiembros() {
        return cantidadMiembros;
    }

    /**
     * Repone los ingredientes según la cantidad dada.
     * 
     * @param administrador    el administrador que repone
     * @param envasesJugo      la cantidad de envases
     * @param paquetesLevadura la cantidad de paquetes
     * @throws InterruptedException
     * @return verdadero si debe seguir reponiendo, falso en caso contrario
     */
    public boolean reponer(Administrador administrador, int envasesJugo,
            int paquetesLevadura) throws InterruptedException {
        boolean seguirReponiendo = true;
        cerrojo.lock();
        try {
            while (cantidadVinosFabricados < cantidadMiembros
                    && (this.envasesJugo >= 2 && this.paquetesLevadura >= 1))
                reponerIngredientes.await();

            if (cantidadVinosFabricados < cantidadMiembros) {
                this.envasesJugo += envasesJugo;
                this.paquetesLevadura += paquetesLevadura;
                System.out.println(
                        administrador.getNombre() + ">>> repone ingredientes");
                hayIngredientes.signalAll();
            } else {
                seguirReponiendo = false;
            }
        } finally {
            cerrojo.unlock();
        }
//        debeReponer.acquire();
//        mutex.acquire();
//        if (cantidadVinosFabricados < cantidadMiembros
//                && (envaseJugo.availablePermits() < 2
//                        || paqueteLevadura.availablePermits() < 1)) {
//            envaseJugo.release(envasesJugo);
//            paqueteLevadura.release(paquetesLevadura);
//            
//            System.out.println(
//                    administrador.getNombre() + ">>> repone ingredientes");
//        } else {
//            continuar = false;
//        }
//        mutex.release();

        return seguirReponiendo;
    }

    /**
     * Simula un miembro entrar por la puerta del almacén.
     * 
     * @param miembro el miembro
     * @throws InterruptedException
     */
    public void entrar(Miembro miembro) throws InterruptedException {
        // TODO: entrar()
    }

    /**
     * Simula un miembro salir por la puerta del almacén.
     */
    public void salir() {
        // TODO: salir()
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
        cerrojo.lock();
        try {
            if (estacionesMezcla >= 1 && jarras >= 2) {
                estacionesMezcla--;
                jarras -= 2;
                while (envasesJugo < 2 || paquetesLevadura < 1) {
                    reponerIngredientes.signal();
                    hayIngredientes.await();
                }
                envasesJugo -= 2;
                paquetesLevadura--;
                System.out.println(
                        Thread.currentThread().getName() + ">>> inicia mezcla");
                inicioMezcla = true;
            }
        } finally {
            cerrojo.unlock();
        }

//        if (estacionMezcla.tryAcquire()) {
//            jarra.acquire(2);
//            if (!envaseJugo.tryAcquire(2)) {
//                debeReponer.release();
//                envaseJugo.acquire(2);
//            }
//            if (!paqueteLevadura.tryAcquire()) {
//                debeReponer.release();
//                paqueteLevadura.acquire();
//            }
//            System.out.println(
//                    Thread.currentThread().getName() + ">>> inicia mezcla");
//            inicioMezcla = true;
//        }

        return inicioMezcla;
    }

    /**
     * Simula finalizar la mezcla de un miembro. Liber una estación.
     * 
     * @throws InterruptedException
     */
    public void finalizarMezcla() throws InterruptedException {
        cerrojo.lock();
        try {
            estacionesMezcla++;
            System.out.println(
                    Thread.currentThread().getName() + ">>> finaliza mezcla");
        } finally {
            cerrojo.unlock();
        }
//        System.out.println(
//                Thread.currentThread().getName() + ">>> finaliza mezcla");
//
//        estacionMezcla.release();
    }

    private UnidadFermentacion obtenerUnidadFermentacion() {
        UnidadFermentacion unidad = null;
        for (int i = 0; i < unidadesFermentacion.length; i++) {
            if (!unidadesFermentacion[i].estaOcupada()) {
                unidad = unidadesFermentacion[i];
                break;
            }
        }

        return unidad;
    }

    /**
     * Intenta iniciar la fermentación de vino para un miembro. Si no hay una
     * unidad de fermentación disponible, entonces devuelve nulo.
     * 
     * @param miembro
     * @return
     * @throws InterruptedException
     */
    public Fermentacion adquirirUnidadFermentacion(Miembro miembro)
            throws InterruptedException {
        UnidadFermentacion unidadAdquirida;
        Fermentacion fermentacion = null;
        cerrojo.lock();
        try {
            unidadAdquirida = obtenerUnidadFermentacion();
            if (unidadAdquirida != null) {
                unidadAdquirida.ocupar(miembro);
                fermentacion = new Fermentacion(miembro, unidadAdquirida);
                jarras++;

                System.out.println(miembro.getNombre()
                        + ">>> adquiere una unidad de fermentación");
            }
        } finally {
            cerrojo.unlock();
        }
//        if (unidadFermentacion.tryAcquire()) {
//            mutex.acquire();
//            for (int i = 0; i < unidadesFermentacion.length; i++) {
//                if (!unidadesFermentacion[i].estaOcupada()) {
//                    unidadAdquirida = unidadesFermentacion[i];
//                    unidadAdquirida.ocupar(miembro);
//                    fermentacion = new Fermentacion(miembro, unidadAdquirida);
//                    break;
//                }
//            }
//
//            jarra.release();
//            System.out.println(miembro.getNombre()
//                    + ">>> adquiere una unidad de fermentación");
//            mutex.release();
//        }

        return fermentacion;
    }

    public Vino liberarUnidadFermentacion(Fermentacion fermentacion)
            throws InterruptedException {
        UnidadFermentacion uf;
        Vino vino = null;
//        mutex.acquire();
//        uf = unidadesFermentacion[fermentacion.getIdUnidadFermentacion()];
//        vino = uf.getVino();
//        uf.desocupar();
//        cantidadVinosFabricados++;
//        debeReponer.release();
//        unidadFermentacion.release();
//        jarra.release();
//        mutex.release();
        cerrojo.lock();
        try {
            uf = unidadesFermentacion[fermentacion.getIdUnidadFermentacion()];
            vino = uf.getVino();
            uf.desocupar();
            System.out.println(fermentacion.getMiembro().getNombre()
                    + ">>> libera una unidad de fermentación");
            jarras++;
            cantidadVinosFabricados++;
            reponerIngredientes.signal();
            vinosParaProbar.add(vino);
            vinoProbado.signalAll();
        } finally {
            cerrojo.unlock();
        }

        return vino;
    }

    /**
     * Simula la espera de un miembro a que se pruebe su vino. Antes de poder
     * hacer más vino, su vino debe ser probado por todos los miembros, incluido
     * el mismo fabricante.
     * 
     * @param vino
     * @return
     * @throws InterruptedException
     */
    public boolean esperarPruebaVino(Vino vino) throws InterruptedException {
        boolean probaronVino = false;
        cerrojo.lock();
        try {
            Miembro fabricante = vino.getFabricante();

            // Esperar que el resto de los miembros prueben el vino, o que haya
            // un nuevo vino para probar por el fabricante
            while (vino.getCantidadProbaron() < cantidadMiembros
                    || vinosParaProbar.getLast().estaProbadoPor(fabricante))
                vinoProbado.await();

            // Verificar el motivo por el cual se "despertó" este miembro
            if (vino.getCantidadProbaron() >= cantidadMiembros) {
                probaronVino = true;
                System.out.println(fabricante.getNombre() + ">>> vino probado");
            }
        } catch (NoSuchElementException e) {
            // e.printStackTrace();
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
            if (vinosParaProbar.isEmpty())
                hayVinoFabricado.await(2, TimeUnit.SECONDS);

            // Si aún no hay vinos, salir de la espera y seguir su proceso
            if (!vinosParaProbar.isEmpty()) {
                Vino vino;
                Iterator<Vino> iterador = vinosParaProbar.iterator();
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
