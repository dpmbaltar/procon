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
     * Indica si el visitante va y vuelve en tour.
     */
    private boolean enTour = false;

    /**
     * Cantidad de pases a los restaurantes.
     */
    private int paseRestaurantes = 2;

    /**
     * Lugar adquirido para "Nado con delfines".
     */
    private int lugarNadoDelfines = -1;

    /**
     * Indica si ya realizó la actividad "Nado con delfines".
     */
    private boolean realizoNadoDelfines = false;

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
        try {
            VistaParque.getInstancia().agregarHilo();
            irAlParque();

            if (parque.entrar()) {
                int actividad = -1;

                // Ir a las actividades mientras estén abiertas
                while (parque.actividadesAbiertas()) {
                    actividad = elegirActividad();

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
                    case 3:
                        irASnorkel();
                        break;
                    case 4:
                        irANadoConDelfines();
                        break;
                    default:
                        irAlShop();
                    }
                }

                parque.salir();
            }

            volverDelParque();
            VistaParque.getInstancia().sacarHilo();
        } catch (InterruptedException | BrokenBarrierException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Selecciona la actividad a realizar.
     *
     * @return la actividad elegida
     */
    private int elegirActividad() {
        int actividad = ThreadLocalRandom.current().nextInt(0, 6);
        int horaActual = parque.getTiempo().getHora();

        if (!realizoNadoDelfines && ThreadLocalRandom.current().nextInt(0, 100) > 90) {
            actividad = 4;
        }

        if (lugarNadoDelfines >= 0) {
            int horaInicio = parque.getNadoDelfines().obtenerHorarioInicio(lugarNadoDelfines);
            int horasRestantes = horaInicio - horaActual;

            // Si ya es la hora de inicio, entonces pierde el lugar
            // Si falta menos de 2 horas, ir a la actividad a nado con delfines
            if (horasRestantes <= 0) {
                lugarNadoDelfines = -1;
            } else if (horasRestantes <= 2) {
                actividad = 4;
            }
        } else if (paseRestaurantes == 2 && horaActual >= 12 && ThreadLocalRandom.current().nextBoolean()) {
            actividad = 1;
        }

        return actividad;
    }

    /**
     * Ir al parque.
     *
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    private void irAlParque() throws InterruptedException, BrokenBarrierException {
        VistaParque vista = VistaParque.getInstancia();
        String visitante = Thread.currentThread().getName();
        enTour = parque.getTour().iniciar();

        // Si no fue en tour, ir en particular
        if (!enTour) {
            vista.printParque(String.format("%s va al parque en particular", visitante));
            Thread.sleep(Tiempo.entreMinutos(30, 45));
            vista.printParque(String.format("%s llega al parque en particular", visitante));
            Thread.sleep(Tiempo.enMinutos(5));
        }
    }

    /**
     * Irse del parque.
     *
     * @throws BrokenBarrierException
     * @throws InterruptedException
     */
    private void volverDelParque() throws InterruptedException, BrokenBarrierException {
        VistaParque vista = VistaParque.getInstancia();

        if (enTour) {
            parque.getTour().finalizar();
        } else {
            vista.printParque(String.format("%s deja el parque en particular", Thread.currentThread().getName()));
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
            Thread.sleep(Tiempo.entreMinutos(30, 60));

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
        if (paseRestaurantes > 0) {
            paseRestaurantes--;

            try {
                Restaurante restaurante = parque.getRestaurante(ThreadLocalRandom.current().nextInt(0, 3));
                restaurante.entrar();
                Thread.sleep(Tiempo.entreMinutos(45, 60));
                restaurante.salir();
            } catch (InterruptedException e) {
                Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    /**
     * Ir a la actividad "Carrera de gomones por el río".
     */
    private void irACarreraDeGomones() {
        CarreraGomones carrera = parque.getCarreraGomones();
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

    /**
     * Ir a la actividad "Nado con delfines".
     */
    private void irANadoConDelfines() {
        NadoDelfines nadoDelfines = parque.getNadoDelfines();

        try {
            if (!realizoNadoDelfines && lugarNadoDelfines < 0) {
                lugarNadoDelfines = nadoDelfines.adquirirLugar();
            }

            if (lugarNadoDelfines >= 0) {
                int horaActual = parque.getTiempo().getHora();
                int horaInicio = nadoDelfines.obtenerHorarioInicio(lugarNadoDelfines);
                int horasRestantes = horaInicio - horaActual;

                // Esperar por la actividad si falta menos de 2 horas
                if (horasRestantes <= 2) {

                    // Está por iniciar, ir a la actividad
                    if (nadoDelfines.entrarPileta(lugarNadoDelfines)) {
                        nadoDelfines.salirPileta(lugarNadoDelfines);
                        lugarNadoDelfines = -1;
                        realizoNadoDelfines = true;
                    }
                }
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
