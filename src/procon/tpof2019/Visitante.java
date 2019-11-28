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
     * Lugar adquirido para "Nado con delfines".
     */
    private int lugarNadoDelfines = -1;

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
            //irAlParque();
            if (parque.iniciarVisita()) {
                parque.iniciarTour();
                parque.finalizarTour();
            } else {
                parque.irParticular();
                Thread.sleep(Tiempo.entreMinutos(30, 60));
            }

            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 10) * 100);
            parque.entrar();

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

                Thread.sleep(Tiempo.entreMinutos(0, 10));
            }

            parque.finalizarVisita();
            //volverDelParque();
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

        if (lugarNadoDelfines >= 0) {
            int horaInicio = parque.getNadoDelfines().obtenerHorarioInicio(lugarNadoDelfines);
            int horasRestantes = horaInicio - horaActual;

            // Si ya es la hora de inicio, entonces pierde el lugar
            // Si falta menos de 1 hora y media, ir a la actividad a nado con delfines
            if (horasRestantes <= 0) {
                lugarNadoDelfines = -1;
            } else if (horasRestantes == 1 && parque.getTiempo().getMinuto() <= 30) {
                actividad = 4;
            }
        } else if (paseRestaurantes == 2 && horaActual >= 12) {
            actividad = 1;
        }

        return actividad;
    }

    /**
     * Ir al parque.
     *
     * @throws InterruptedException
     */
    private void irAlParque() throws InterruptedException {
        //TODO: irAlParque()
        Tour tour;// = parque.getTour();
        VistaParque vista = VistaParque.getInstancia();
        String visitante = Thread.currentThread().getName();

        vista.printParque(String.format("%s va al parque", visitante));
        Thread.sleep(Tiempo.entreMinutos(30, 60));
        vista.printParque(String.format("%s llega al parque", visitante));
    }

    /**
     * Irse del parque.
     */
    private void volverDelParque() {
        //TODO: volverDelParque()
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
            if (lugarNadoDelfines < 0) {
                lugarNadoDelfines = nadoDelfines.adquirirLugar();
            }

            if (lugarNadoDelfines >= 0) {
                int horaActual = parque.getTiempo().getHora();
                int horaInicio = nadoDelfines.obtenerHorarioInicio(lugarNadoDelfines);
                int horasRestantes = horaInicio - horaActual;

                // Esperar por la actividad si falta menos de hora y media
                if ((horasRestantes < 1 || (horasRestantes == 1 && parque.getTiempo().getMinuto() > 30))) {

                    // Está por iniciar, ir a la actividad
                    if (nadoDelfines.entrarPileta(lugarNadoDelfines)) {
                        nadoDelfines.salirPileta(lugarNadoDelfines);
                        lugarNadoDelfines = -1;
                    }
                }
            }
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
