package procon.tpf1.e2;

import java.util.concurrent.Semaphore;

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
    private boolean vinoListo = false;

    /**
     * Constructor con las especificaciónes del almacen.
     * 
     * @param estacionesMezcla     cantidad de estaciones de mezcla
     * @param unidadesFermentacion cantidad de unidades de fermentación
     * @param jarras               cantidad de jarras (10 litros c/u)
     * @param envasesJugo          cantidad de envases de jugo (5 litros c/u)
     * @param paquetesLevadura     cantidad de levadura (10 litros de vino c/u)
     */
    public Almacen(int estacionesMezcla, int unidadesFermentacion, int jarras,
            int envasesJugo, int paquetesLevadura) {
        this.estacionesMezcla = estacionesMezcla;
        this.unidadesFermentacion = new UnidadFermentacion[unidadesFermentacion];
        for (int i = 1; i <= unidadesFermentacion; i++)
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

    public int finalizarMezcla() throws InterruptedException {
        System.out.println(
                Thread.currentThread().getName() + ">>> finaliza mezcla");
        estacionMezcla.release();

        return 10;
    }

    public UnidadFermentacion adquirirUnidadFermentacion()
            throws InterruptedException {
        UnidadFermentacion unidadAdquirida = null;

        return unidadAdquirida;
    }
    
    public void liberarUnidadFermentacion(UnidadFermentacion uf)
            throws InterruptedException {
        unidadesFermentacion[uf.getId() - 1] = uf;
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

    public void esperarPruebaVino() {

    }

    public void salir() {

    }

    public void probarVinoSiHay() {
        if (vinoParaProbar.tryAcquire()) {
            System.out.println(
                    Thread.currentThread().getName() + ">>> prueba vino");
        }
    }

    public synchronized void probarVino() throws InterruptedException {
        while (!vinoListo)
            wait();
    }

}
