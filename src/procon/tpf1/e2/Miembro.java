package procon.tpf1.e2;

import java.util.concurrent.ThreadLocalRandom;

public class Miembro implements Runnable {

    protected final String nombre;
    protected final Almacen almacen;

    public Miembro(String nombre, Almacen almacen) {
        this.nombre = nombre;
        this.almacen = almacen;
    }

    public synchronized String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        try {
            Thread hiloFermentacion = null;
            Fermentacion fermentacion = null;
            UnidadFermentacion unidadFerm = null;
            int litrosFabricados = 0;
            int espera = -1;
            int estapa = 0;

            while (litrosFabricados < 10) {
                // almacen.probarVinoSiHay();
                switch (estapa) {
                case 0: // Prepara mezcla
                    if (almacen.iniciarMezcla()) {
                        Thread.sleep(150);
                        litrosFabricados += almacen.finalizarMezcla();
                        estapa++;
                    }
                    break;
                case 1: // Inicia fermentación
                    unidadFerm = almacen.adquirirUnidadFermentacion();
                    if (unidadFerm != null) {
                        fermentacion = new Fermentacion(this, unidadFerm);
                        hiloFermentacion = new Thread(fermentacion);
                        hiloFermentacion.start();
                        estapa++;
                    }
                    break;
                case 2: // Finaliza fermentación
                    if (fermentacion.estaFinalizada()) {
                        almacen.liberarUnidadFermentacion(unidadFerm);
                        hiloFermentacion = null;
                        fermentacion = null;
                        unidadFerm = null;
                        // ???
                        estapa++;
                    }
                    break;
                case 3: // espera pasiva
                    //almacen.esperarPruebaVino();
                    break;
                }

                almacen.probarVinos(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
