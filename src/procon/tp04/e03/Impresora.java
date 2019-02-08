package procon.tp04.e03;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Una impresora que imprime trabajos desde el servicio de impresion.
 */
public class Impresora implements Runnable {
    public static enum Tipo {
        A, B, CUALQUIERA;
    }

    private final ServicioImpresion servicioImpresion;
    private Tipo tipo;
    private int id;

    public Impresora(ServicioImpresion servicioImpresion, Tipo tipo, int id) {
        this.servicioImpresion = servicioImpresion;
        this.tipo = tipo;
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Impresion impresion;
        try {
            while (true) {
                impresion = servicioImpresion.imprimir(this);
                System.out.println(
                        String.format("Impresora %s #%d comienza a imprimir %s",
                                tipo, id, impresion));
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 5) * 100);
                System.out.println(
                        String.format("Impresora %s #%d termina de imprimir %s",
                                tipo, id, impresion));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
