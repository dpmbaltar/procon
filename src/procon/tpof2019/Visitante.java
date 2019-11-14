package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Visitante del parque.
 *
 * @author Diego P. M. Baltar {@literal <dpmbaltar@gmail.com>}
 */
public class Visitante implements Runnable {

    /**
     * Instancia del parque.
     */
    private final Parque parque;

    /**
     * Constructor con el parque.
     *
     * @param parque el parque
     */
    public Visitante(Parque parque) {
        this.parque = parque;
    }

    /**
     * Visita el parque.
     */
    @Override
    public void run() {
        int actividad = -1;

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
            parque.iniciarVisita();
            Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);

            while (parque.actividadesAbiertas()) {
                actividad = ThreadLocalRandom.current().nextInt(0, 5);

                // Realizar actividad
                switch (actividad) {
                case 0:
                    irACarreraDeGomones();
                    break;
                case 1:
                    irAlRestaurante();
                    break;
                case 2:
                    irAFaroMirador();
                    break;
                default:
                    irAlShop();
                }

                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
            }

            parque.finalizarVisita();
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Ir al shop y comprar (opcionalmente).
     */
    private void irAlShop() {
        boolean comprar = ThreadLocalRandom.current().nextBoolean();
        int caja = -1;
        Shop shop = parque.getShop();

        try {
            shop.entrar();
            Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20) * 100);

            if (comprar) {
                caja = shop.comprar();
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 2) * 100);
                shop.pagar(caja);
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 3) * 100);
            }

            shop.salir();
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Ir al restaurante a almorzar o merendar.
     */
    private void irAlRestaurante() {
        Restaurante restaurante = parque.getRestaurante(ThreadLocalRandom.current().nextInt(0, 3));

        try {
            restaurante.entrar();
            Thread.sleep(ThreadLocalRandom.current().nextInt(15, 20) * 100);
            restaurante.salir();
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Ir a la actividad "Carrera de gomones por el río".
     */
    private void irACarreraDeGomones() {
        CarreraGomones carrera = parque.getCarreraGomones();
        int llaveDeBolso = -1;
        int gomon = -1;
        boolean irEnTren = false;//ThreadLocalRandom.current().nextBoolean();

        try {
            carrera.irACarrerasDeGomones(irEnTren);
            llaveDeBolso = carrera.dejarPertenencias();
            gomon = carrera.subirseAGomon();
            carrera.iniciarCarreraDeGomones();
            carrera.finalizarCarreraDeGomones(llaveDeBolso, gomon);
            carrera.volverDeCarrerasDeGomones(irEnTren);
        } catch (InterruptedException | BrokenBarrierException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Ir a la actividad "Faro-Mirador con vista a 40 m de altura y descenso en tobogán".
     */
    private void irAFaroMirador() {
        FaroMirador faroMirador = parque.getFaroMirador();
        int tobogan = -1;

        try {
            faroMirador.irAFaroMirador();
            faroMirador.iniciarAscensoPorEscalera();
            faroMirador.finalizarAscensoPorEscalera();
            tobogan = faroMirador.iniciarDescensoEnTobogan();
            faroMirador.finalizarDescensoEnTobogan(tobogan);
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
