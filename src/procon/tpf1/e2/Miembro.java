package procon.tpf1.e2;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Un miembro del club, que se encarga de fabricar y probar vinos.
 * 
 * @author Diego P. M. Baltar <dpmbaltar@gmail.com>
 */
public class Miembro implements Runnable {

    /**
     * El nombre del miembro.
     */
    protected final String nombre;

    /**
     * El almacén del club.
     */
    protected final Almacen almacen;

    /**
     * Constructor con el nombre del miembro y el almacén.
     * 
     * @param nombre  el nombre
     * @param almacen el almacén
     */
    public Miembro(String nombre, Almacen almacen) {
        this.nombre = nombre;
        this.almacen = almacen;
    }

    /**
     * Devuelve el nombre del miembro.
     * 
     * @return el nombre del miembro
     */
    public synchronized String getNombre() {
        return nombre;
    }

    /**
     * Fabrica y prueba vinos.
     */
    @Override
    public void run() {
        try {
            Thread hiloFermentacion = null;
            Fermentacion fermentacion = null;
            Vino vino = null;
            int cantidadMiembros = almacen.getCantidadMiembros();
            int vinosProbados = 0;
            int etapa = 0;

            while (vinosProbados < cantidadMiembros) {
                switch (etapa) {
                case 0: // Prepara mezcla
                    if (almacen.iniciarMezcla()) {
                        Thread.sleep(150);
                        almacen.finalizarMezcla();
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

                vinosProbados += almacen.probarVinos(this);
            }
            System.out.println(getNombre() + ">>> termina con " + vinosProbados
                    + " vinos probados");
        } catch (InterruptedException e) {
            System.out.println(getNombre() + ">>> miembro interrumpido");
        }
    }

}
