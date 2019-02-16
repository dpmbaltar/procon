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
            Vino vino = null;
            int litrosFabricados = 0;
            int vinosProbados = 0;
            int etapa = 0;

            while (vinosProbados < 10) {
                // almacen.probarVinoSiHay();
//                System.out.println(Thread.currentThread().getName()
//                        + ">>> pre switch() etapa: " + etapa + " vinosProbados: "
//                        + vinosProbados);
                switch (etapa) {
                case 0: // Prepara mezcla
                    if (almacen.iniciarMezcla()) {
                        Thread.sleep(150);
                        litrosFabricados += almacen.finalizarMezcla();
                        etapa++;
                    }
                    break;
                case 1: // Inicia fermentación
                    fermentacion = almacen.adquirirUnidadFermentacion(this);
                    if (fermentacion != null) {
                        hiloFermentacion = new Thread(fermentacion);
                        hiloFermentacion.start();
                        etapa++;
                    }
                    break;
                case 2: // Finaliza fermentación
                    if (fermentacion.estaCompleta()) {
                        vino = almacen.liberarUnidadFermentacion(fermentacion);
                        hiloFermentacion = null;
                        fermentacion = null;
                        etapa++;
                    }
                    break;
                case 3: // Espera a que todos prueben su vino
                    if (almacen.esperarPruebaVino(vino)) {
                        vino = null;
                        etapa++;
                    }
                    break;
                }
                // System.out.println("pre probarVinos() etapa: " + etapa);
                vinosProbados += almacen.probarVinos(this);
//                System.out.println(Thread.currentThread().getName()
//                        + ">>> post probarVinos() etapa: " + etapa);
            }
            System.out.println(nombre + ">>> termina con " + vinosProbados
                    + " vinos probados");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
