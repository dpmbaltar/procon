package procon.tpof2019;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            while (parque.actividadesAbiertas()) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(5, 10) * 100);
                //irACarreraDeGomones();
                //irAFaroMirador();
                //irAlRestaurante();
                irAlShop();
            }
            System.out.println(Thread.currentThread().getName() + " termina");
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

            if (comprar)
                caja = shop.comprar();

            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 3) * 100);
            shop.salir(caja);
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

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

    private void irACarreraDeGomones() {
        int llaveDeBolso = -1;
        int gomon = -1;
        boolean irEnTren = false;//ThreadLocalRandom.current().nextBoolean();

        try {
            parque.irACarrerasDeGomones(irEnTren);
            llaveDeBolso = parque.dejarPertenencias();
            gomon = parque.subirseAGomon();
            parque.iniciarCarreraDeGomones();
            parque.finalizarCarreraDeGomones(llaveDeBolso, gomon);
            parque.volverDeCarrerasDeGomones(irEnTren);
        } catch (InterruptedException | BrokenBarrierException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void irAFaroMirador() {
        int tobogan = -1;

        try {
            parque.irAFaroMirador();
            parque.iniciarAscensoPorEscalera();
            parque.finalizarAscensoPorEscalera();
            tobogan = parque.iniciarDescensoEnTobogan();
            parque.finalizarDescensoEnTobogan(tobogan);
        } catch (InterruptedException e) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
