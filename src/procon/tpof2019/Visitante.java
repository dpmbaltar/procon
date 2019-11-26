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
     * Cantidad de pases a los restaurantes.
     */
    private int paseRestaurantes = 2;

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
            if (parque.iniciarVisita()) {
                parque.iniciarTour();
                parque.finalizarTour();
            } else {
                parque.irParticular();
                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
            }

            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 10) * 100);
            parque.entrar();

            while (parque.actividadesAbiertas()) {
                actividad = ThreadLocalRandom.current().nextInt(0, 10);
                irASnorkel();
                // Realizar actividad
                /*switch (actividad) {
                case 0:
                    irACarreraDeGomones();
                    break;
                case 1:
                    if (paseRestaurantes > 0) {
                        irAlRestaurante();
                        paseRestaurantes--;
                    }
                    break;
                case 2:
                    irAFaroMirador();
                    break;
                default:
                    irAlShop();
                }*/

                Thread.sleep(Tiempo.entreMinutos(15, 30));
            }

            parque.finalizarVisita();
        } catch (InterruptedException | BrokenBarrierException e) {
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
            Thread.sleep(Tiempo.entreMinutos(30, 90));

            // Comprar souvenir
            if (comprar) {
                caja = shop.comprar();
                Thread.sleep(Tiempo.entreMinutos(5, 10));
                shop.pagar(caja);
                Thread.sleep(Tiempo.entreMinutos(0, 10));
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
            Thread.sleep(Tiempo.entreHoras(1, 2));
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
        int[] itemsOcupados = { -1, -1 };
        boolean irEnTren = ThreadLocalRandom.current().nextBoolean();
        boolean quiereGomonDoble = ThreadLocalRandom.current().nextBoolean();

        try {
            if (irEnTren && carrera.irEnTren()) {
                itemsOcupados = carrera.prepararse(quiereGomonDoble);
                carrera.iniciarCarrera();
                carrera.finalizarCarrera(itemsOcupados);
                carrera.volverEnTren();
            } else if (carrera.irEnBicicleta()) {
                itemsOcupados = carrera.prepararse(quiereGomonDoble);
                carrera.iniciarCarrera();
                carrera.finalizarCarrera(itemsOcupados);
                carrera.volverEnBicicleta();
            }
        } catch (InterruptedException e) {
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
            faroMirador.iniciarAscensoPorEscalera();
            faroMirador.finalizarAscensoPorEscalera();
            tobogan = faroMirador.iniciarDescensoEnTobogan();
            faroMirador.finalizarDescensoEnTobogan(tobogan);
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Ir a la actividad "Disfruta de Snorkel ilimitado".
     */
    private void irASnorkel() {
        Snorkel snorkel = parque.getSnorkel();

        try {
            if (snorkel.adquirirEquipo()) {
                snorkel.iniciar();
                snorkel.finalizar();
                snorkel.devolverEquipo();
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
