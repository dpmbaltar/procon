package procon.tp04.e03;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Un usuario que solicita impresiones al servicio de impresion.
 */
public class Usuario implements Runnable {

    private final ServicioImpresion servicioImpresion;

    public Usuario(ServicioImpresion servicioImpresion) {
        this.servicioImpresion = servicioImpresion;
    }

    @Override
    public void run() {
        Impresion impresion;
        try {
            while (true) {
                impresion = Impresion.aleatoria();
                servicioImpresion.solicitar(impresion);
                Thread.sleep(ThreadLocalRandom.current().nextInt(10, 15) * 100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
